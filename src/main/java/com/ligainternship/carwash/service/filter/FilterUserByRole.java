package com.ligainternship.carwash.service.filter;

import com.ligainternship.carwash.exception.UserNotFoundException;
import com.ligainternship.carwash.model.entitiy.Role;
import com.ligainternship.carwash.model.entitiy.Role_;
import com.ligainternship.carwash.model.entitiy.User;
import com.ligainternship.carwash.model.entitiy.User_;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Component
@Slf4j
public class FilterUserByRole {

    @PersistenceContext
    EntityManager entityManager;

    public User findByIdAndRole(Long id, String role) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<Role> root = query.from(Role.class);
        Join<Role, User> roleJoin = root.join(Role_.users);
        query.select(roleJoin);
        query.where(criteriaBuilder.and(
                criteriaBuilder.equal(roleJoin.get(User_.id), id),
                criteriaBuilder.equal(root.get(Role_.name), role)));
        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        List<User> userList = typedQuery.getResultList();
        if (userList.isEmpty()) {
            String message = "User with this id and role not found";
            log.error(message);
            throw new UserNotFoundException(message);
        }
        return typedQuery
                .setMaxResults(1)
                .getSingleResult();
    }

}
