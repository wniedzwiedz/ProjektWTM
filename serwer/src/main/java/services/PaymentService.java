package services;

import models.Group;
import models.Payment;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Stateless
public class PaymentService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Payment> getAll() {
        TypedQuery<Payment> query = entityManager.createQuery("select p from Payment p", Payment.class);
        return query.getResultList();
    }

    public Payment getById(int id) {
        TypedQuery<Payment> query = entityManager.createQuery("select p from Payment p where p.id = :id", Payment.class);
        query.setParameter("id", id);
        List<Payment> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public List<Payment> getByUserId(int id) {
        TypedQuery<Payment> query = entityManager.createQuery("select p from Payment p where p.user.id = :id", Payment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Payment> getByGroupId(int id) {
        TypedQuery<Payment> query = entityManager.createQuery("select p from Payment p where p.group.id = :id", Payment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Payment> getByUserIdNotPaid(int id) {
        TypedQuery<Payment> query = entityManager.createQuery("select p from Payment p where p.user.id = :id and p.isPaid = false", Payment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Payment> getByGroupIdNotPaid(int id) {
        TypedQuery<Payment> query = entityManager.createQuery("select p from Payment p where p.group.id = :id and p.isPaid = false", Payment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }


    @Transactional
    public Payment insert(Payment payment) {
        Payment newPayment = new Payment();
        if (payment.getDeadline() != null) {
            newPayment.setDeadline(payment.getDeadline());
        }
        if (payment.getPrice() != 0) {
            newPayment.setPrice(payment.getPrice());
        }
        if (payment.getUser() != null && payment.getUser().getId() != 0) {
            newPayment.setUser(payment.getUser());
        }
        if (payment.getGroup() != null && payment.getGroup().getId() != 0) {
            newPayment.setGroup(payment.getGroup());
        }
        if (payment.isPaid()) {
            newPayment.setPaid(payment.isPaid());
        } else {
            newPayment.setPaid(false);
        }
        entityManager.persist(newPayment);
        return newPayment;
    }

    @Transactional
    public Payment deleteById(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Payment> criteriaQuery = criteriaBuilder.createQuery(Payment.class);
        Root<Payment> root = criteriaQuery.from(Payment.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<Payment> typedQuery = entityManager.createQuery(criteriaQuery);
        if (!typedQuery.getResultList().isEmpty()) {
            Payment p = typedQuery.getSingleResult();
            entityManager.remove(p);
            return p;
        } else {
            return null;
        }
    }

    @Transactional
    public Payment update(Payment payment, boolean isNotPaid) {
        Payment p = entityManager.find(Payment.class, payment.getId());
        if (p == null) {
            return null;
        }
        entityManager.merge(p);
        if (payment.getDeadline() != null) {
            p.setDeadline(payment.getDeadline());
        }
        if (payment.getPrice() != 0) {
            p.setPrice(payment.getPrice());
        }
        if (payment.getUser() != null && payment.getUser().getId() != 0) {
            p.setUser(payment.getUser());
        }
        if (payment.getGroup() != null && payment.getGroup().getId() != 0) {
            p.setGroup(payment.getGroup());
        }
        if (payment.isPaid()) {
            p.setPaid(true);
        } else if(isNotPaid) {
            p.setPaid(false);
        }
        return p;
    }

}
