package services;

import models.Application;
import models.Group;
import models.User;
import models.Package;

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
public class PackageService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Package> getAll() {
        TypedQuery<Package> query = entityManager.createQuery("select p from Package p", Package.class);
        return query.getResultList();
    }

    public Package getById(int id) {
        TypedQuery<Package> query = entityManager.createQuery("select p from Package p where p.id = :id", Package.class);
        query.setParameter("id", id);
        List<Package> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public List<Package> getByNameLike(String name) {
        TypedQuery<Package> query = entityManager.createQuery("select p from Package p where lower(p.name) like lower(:name)", Package.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    public List<Package> getByApplicationId(int id) {
        TypedQuery<Package> query = entityManager.createQuery("select p from Package p join p.application a where a.id = :id", Package.class);
        query.setParameter("id", id);
        return query.getResultList();
    }


    @Transactional
    public Package insert(Package aPackage) {
        Package newPackage = new Package();
        if (aPackage.getName() != null) {
            newPackage.setName(aPackage.getName());
        }
        if (aPackage.getApplication() != null && aPackage.getApplication().getId() != 0) {
            newPackage.setApplication(aPackage.getApplication());
        }
        entityManager.persist(newPackage);
        return newPackage;
    }

    @Transactional
    public Package deleteById(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Package> criteriaQuery = criteriaBuilder.createQuery(Package.class);
        Root<Package> root = criteriaQuery.from(Package.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<Package> typedQuery = entityManager.createQuery(criteriaQuery);
        if (!typedQuery.getResultList().isEmpty()) {
            Package p = typedQuery.getSingleResult();
            entityManager.remove(p);
            return p;
        } else {
            return null;
        }
    }

    @Transactional
    public Package update(Package aPackage) {
        Package p = entityManager.find(Package.class, aPackage.getId());
        if (p == null) {
            return null;
        }
        entityManager.merge(p);
        if (aPackage.getName() != null) {
            p.setName(aPackage.getName());
        }
        if (aPackage.getApplication() != null && aPackage.getApplication().getId() != 0) {
            p.setApplication(aPackage.getApplication());
        }
        return p;
    }
}
