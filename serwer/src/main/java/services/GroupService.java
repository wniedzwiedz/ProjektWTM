package services;

import models.*;

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
public class GroupService {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Group> getAll() {
        TypedQuery<Group> query = entityManager.createQuery("select g from Group g", Group.class);
        return query.getResultList();
    }

    public Group getById(int id) {
        TypedQuery<Group> query = entityManager.createQuery("select g from Group g where g.id = :id", Group.class);
        query.setParameter("id", id);
        List<Group> resultList = query.getResultList();
        if (resultList.isEmpty()) {
            return null;
        } else {
            return resultList.get(0);
        }
    }

    public List<Group> getByNameLike(String name) {
        TypedQuery<Group> query = entityManager.createQuery("select g from Group g where lower(g.name) like lower(:name)", Group.class);
        query.setParameter("name", "%" + name + "%");
        return query.getResultList();
    }

    public List<Group> getByOwnerId(int id) {
        TypedQuery<Group> query = entityManager.createQuery("select g from Group g where g.owner.id = :id", Group.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Group> getByPackageId(int id) {
        TypedQuery<Group> query = entityManager.createQuery("select g from Group g where g.aPackage.id = :id", Group.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Group> getByUserId(int id) {
        TypedQuery<Group> query = entityManager.createQuery("select g from Group g join g.userInGroups uig join uig.user u where u.id = :id", Group.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Group> getConfirmedByUserId(int id) {
        TypedQuery<Group> query = entityManager.createQuery("select g from Group g join g.userInGroups uig join uig.user u " +
                "where u.id = :id and uig.isConfirmed = true", Group.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    public List<Group> getRequestedByUserId(int id) {
        TypedQuery<Group> query = entityManager.createQuery("select g from Group g join g.userInGroups uig join uig.user u " +
                "where u.id = :id and uig.isConfirmed = false", Group.class);
        query.setParameter("id", id);
        return query.getResultList();
    }


    @Transactional
    public Group insert(Group group) {
        Group newGroup = new Group();
        if (group.getName() != null) {
            newGroup.setName(group.getName());
        }
        if (group.getOwner() != null && group.getOwner().getId() != 0) {
            newGroup.setOwner(group.getOwner());
        }
        if (group.getaPackage() != null && group.getaPackage().getId() != 0) {
            newGroup.setaPackage(group.getaPackage());
        }
        if (group.getBankAccountNumber() != null) {
            newGroup.setBankAccountNumber(group.getBankAccountNumber());
        }
        if (group.getMaxNumberOfMembers() != 0) {
            newGroup.setMaxNumberOfMembers(group.getMaxNumberOfMembers());
        }
        if (group.getInformation() != null) {
            newGroup.setInformation(group.getInformation());
        }
        if (group.getPaymentInfo() != null) {
            newGroup.setPaymentInfo(group.getPaymentInfo());
        }
        entityManager.persist(newGroup);
        return newGroup;
    }

    @Transactional
    public Group deleteById(int id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Group> criteriaQuery = criteriaBuilder.createQuery(Group.class);
        Root<Group> root = criteriaQuery.from(Group.class);
        criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id));
        TypedQuery<Group> typedQuery = entityManager.createQuery(criteriaQuery);
        if (!typedQuery.getResultList().isEmpty()) {
            Group g = typedQuery.getSingleResult();
            entityManager.remove(g);
            return g;
        } else {
            return null;
        }
    }

    @Transactional
    public Group update(Group group) {
        Group g = entityManager.find(Group.class, group.getId());
        if (g == null) {
            return null;
        }
        entityManager.merge(g);
        if (group.getName() != null) {
            g.setName(group.getName());
        }
        if (group.getOwner() != null && group.getOwner().getId() != 0) {
            g.setOwner(group.getOwner());
        }
        if (group.getaPackage() != null && group.getaPackage().getId() != 0) {
            g.setaPackage(group.getaPackage());
        }
        if (group.getBankAccountNumber() != null) {
            g.setBankAccountNumber(group.getBankAccountNumber());
        }
        if (group.getMaxNumberOfMembers() != 0) {
            g.setMaxNumberOfMembers(group.getMaxNumberOfMembers());
        }
        if (group.getInformation() != null) {
            g.setInformation(group.getInformation());
        }
        if (group.getPaymentInfo() != null) {
            g.setPaymentInfo(group.getPaymentInfo());
        }
        return g;
    }

    // USER_IN_GROUP

    @Transactional
    public boolean addUser(int groupId, int userId) {
        Group g = entityManager.getReference(Group.class, groupId);
        User u = entityManager.getReference(User.class, userId);
        if (g == null || u == null) {
            return false;
        }

        UserInGroup userInGroup = new UserInGroup();
        UserInGroupId id = new UserInGroupId(u, g);
        userInGroup.setId(id);
        userInGroup.setConfirmed(false);

        entityManager.persist(userInGroup);
        return true;
    }

    @Transactional
    public boolean confirmUser(int groupId, int userId) {
        Group g = entityManager.getReference(Group.class, groupId);
        User u = entityManager.getReference(User.class, userId);
        if (g == null || u == null) {
            return false;
        }

        UserInGroup userInGroup = entityManager.find(UserInGroup.class, new UserInGroupId(u, g));
        entityManager.merge(userInGroup);
        userInGroup.setConfirmed(true);
        return true;
    }

    @Transactional
    public boolean deleteUser(int groupId, int userId) {
        Group g = entityManager.getReference(Group.class, groupId);
        User u = entityManager.getReference(User.class, userId);
        if (g == null || u == null) {
            return false;
        }

        UserInGroup userInGroup = entityManager.find(UserInGroup.class, new UserInGroupId(u, g));
        entityManager.merge(userInGroup);
        entityManager.remove(userInGroup);
        return true;
    }



}
