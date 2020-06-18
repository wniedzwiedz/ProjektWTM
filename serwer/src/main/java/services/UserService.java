package services;

import models.Group;
import models.User;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.List;

@Stateless
public class UserService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<User> getAll() {
        TypedQuery<User> query = entityManager.createQuery("select u from User u", User.class);
        return query.getResultList();
    }

    public User getById(int id) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where u.id = :id", User.class);
        query.setParameter("id", id);
        List<User> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public List<User> getByNameLike(String name) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where lower(u.firstName) like lower(:name) or lower(u.lastName) like lower(:name)", User.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    public List<User> getByEMail(String email) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u where lower(u.email) like lower(:email)", User.class);
        query.setParameter("email", email);
        return query.getResultList();
    }

    public List<User> getByGroupId(int id) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u join u.userInGroups uig join uig.group g where g.id = :id", User.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<User> getConfirmedByGroupId(int id) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u join u.userInGroups uig join uig.group g " +
                "where g.id = :id and uig.isConfirmed = true", User.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<User> getRequestedByGroupId(int id) {
        TypedQuery<User> query = entityManager.createQuery("select u from User u join u.userInGroups uig join uig.group g " +
                "where g.id = :id and uig.isConfirmed = false", User.class);
        query.setParameter("id", id);
        return query.getResultList();
    }



    @Transactional
    public User insert(User user) {
        User newUser = new User();
        if (user.getFirstName() != null) {
            newUser.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            newUser.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            newUser.setEmail(user.getEmail());
        }
        if (user.getPasswordHash() != null) {
            newUser.setPasswordHash(user.getPasswordHash());
        }
        entityManager.persist(newUser);
        return newUser;
    }

    @Transactional
    public User deleteById(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        if (!typedQuery.getResultList().isEmpty()) {
            User u = typedQuery.getSingleResult();
            entityManager.remove(u);
            return u;
        } else {
            return null;
        }
    }

    @Transactional
    public User update(User user) {
        User u = entityManager.find(User.class, user.getId());
        if (u == null) {
            return null;
        }
        entityManager.merge(u);
        if (user.getFirstName() != null) {
            u.setFirstName(user.getFirstName());
        }
        if (user.getLastName() != null) {
            u.setLastName(user.getLastName());
        }
        if (user.getEmail() != null) {
            u.setEmail(user.getEmail());
        }
        if (user.getPasswordHash() != null) {
            u.setPasswordHash(user.getPasswordHash());
        }
        return u;
    }


}
