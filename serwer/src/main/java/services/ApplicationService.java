package services;

import models.Application;
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
public class ApplicationService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Application> getAll() {
        TypedQuery<Application> query = entityManager.createQuery("select a from Application a", Application.class);
        return query.getResultList();
    }

    public Application getById(int id) {
        TypedQuery<Application> query = entityManager.createQuery("select a from Application a where a.id = :id", Application.class);
        query.setParameter("id", id);
        List<Application> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public List<Application> getByNameLike(String name) {
        TypedQuery<Application> query = entityManager.createQuery("select a from Application a where lower(a.name) like lower(:name)", Application.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    public List<Application> getByUserId(int id) {
        TypedQuery<Application> query = entityManager.createQuery("select a from Application a join a.packages p " +
                "join p.groups g join g.userInGroups uig join uig.user u where u.id = :id", Application.class);
        query.setParameter("id", id);
        return query.getResultList();
    }


    @Transactional
    public Application insert(Application application) {
        Application newApplication = new Application();
        if (application.getName() != null) {
            newApplication.setName(application.getName());
        }
        entityManager.persist(newApplication);
        return newApplication;
    }

    @Transactional
    public Application deleteById(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Application> criteriaQuery = criteriaBuilder.createQuery(Application.class);
        Root<Application> root = criteriaQuery.from(Application.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<Application> typedQuery = entityManager.createQuery(criteriaQuery);
        if (!typedQuery.getResultList().isEmpty()) {
            Application a = typedQuery.getSingleResult();
            entityManager.remove(a);
            return a;
        } else {
            return null;
        }
    }

    @Transactional
    public Application update(Application application) {
        Application a = entityManager.find(Application.class, application.getId());
        if (a == null) {
            return null;
        }
        entityManager.merge(a);
        if (application.getName() != null) {
            a.setName(application.getName());
        }
        return a;
    }

}
