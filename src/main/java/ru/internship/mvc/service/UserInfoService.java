package ru.internship.mvc.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.internship.mvc.dto.UserFindMaxTasksDto;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.Task_;
import ru.internship.mvc.model.User;
import ru.internship.mvc.model.User_;
import ru.internship.mvc.repo.UserRepo;
import ru.internship.mvc.service.specifications.FilterByStatusAndDate;
import ru.internship.mvc.model.enums.Status;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserInfoService {

    private final UserRepo userRepo;

    @PersistenceContext
    EntityManager entityManager;

    public User getOne(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public UserFindMaxTasksDto findByMaxTasksCount(String status, Date first, Date second) {
        if (status.equals(Status.DEFAULT_STATUS.getStatus()) ||
                status.equals(Status.WORK_STATUS.getStatus()) ||
                status.equals(Status.COMPLETE_STATUS.getStatus())) {
            Specification<Task> specificationUserFilter = (root, query, cBuilder) -> {
                CriteriaQuery<User> criteriaQuery = cBuilder.createQuery(User.class);
                Root<Task> taskRoot = criteriaQuery.from(Task.class);
                Join<Task, User> taskJoin = taskRoot.join(Task_.user);
                criteriaQuery.select(taskJoin);
                criteriaQuery.groupBy(taskJoin.get(User_.id));
                criteriaQuery.orderBy(cBuilder.desc(cBuilder.count(taskRoot.get(Task_.id))));
                TypedQuery<User> taskTypedQuery = entityManager.createQuery(criteriaQuery);
                return cBuilder.equal(root.get(Task_.user), taskTypedQuery.setMaxResults(1).getSingleResult().getId());
            };
            Specification<Task> specificationTaskFilter = FilterByStatusAndDate.getSpec(status,first,second);

            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Tuple> criteriaQuery = criteriaBuilder.createTupleQuery();
            Root<Task> taskRoot = criteriaQuery.from(Task.class);
            Join<Task, User> taskJoin = taskRoot.join(Task_.user);

            criteriaQuery.select(criteriaBuilder.tuple(taskRoot, taskJoin));
            criteriaQuery.where(criteriaBuilder.and(specificationUserFilter.toPredicate(taskRoot, criteriaQuery, criteriaBuilder),
                    specificationTaskFilter.toPredicate(taskRoot,criteriaQuery,criteriaBuilder)));

            TypedQuery<Tuple> typedQuery = entityManager.createQuery(criteriaQuery);
            List<Tuple> result = typedQuery.getResultList();
            return UserFindMaxTasksDto.mapToUserDto(result);
        }
        else {
            throw new InputMismatchException("Incorrect status");
        }
    }
}
