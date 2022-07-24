package ru.internship.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.internship.mvc.dto.UserFindMaxTasksDTO;
import ru.internship.mvc.model.Task;
import ru.internship.mvc.model.Task_;
import ru.internship.mvc.model.User;
import ru.internship.mvc.model.User_;
import ru.internship.mvc.repo.UserRepo;
import ru.internship.mvc.service.specifications.FilterByStatusAndDate;

import javax.persistence.*;
import javax.persistence.criteria.*;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {

    private static String DEFAULT_STATUS;

    @Value("${statuses.default-status}")
    public void setDefaultStatus(String status) {
        UserService.DEFAULT_STATUS = status;
    }

    private static String WORK_STATUS;

    @Value("${statuses.work-status}")
    public void setWorkStatus(String status) {
        UserService.WORK_STATUS = status;
    }

    private static String COMPLETE_STATUS;

    @Value("${statuses.complete-status}")
    public void setCompleteStatus(String status) {
        UserService.COMPLETE_STATUS = status;
    }

    private final UserRepo userRepo;

    @PersistenceContext(name = "JPA_DEMO", type = PersistenceContextType.TRANSACTION)
    EntityManager entityManager;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User addNewUser(User user) {
        if(isNameValid(user.getName())) {
            return userRepo.save(user);
        } else {
            throw new InputMismatchException("Incorrect user name");
        }
    }

    public String removeUser(Long id) {
        userRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("User with this id doesn't exist"));
        userRepo.deleteById(id);
        return "User: " + id + " deleted";
    }

    public UserFindMaxTasksDTO findByMaxTasksCount(String status, Date first, Date second) {
        if (status.equals(DEFAULT_STATUS) || status.equals(WORK_STATUS) || status.equals(COMPLETE_STATUS)) {
            Specification<Task> specificationUserFilter = (root, query, cBuilder) -> {
                CriteriaQuery<User> criteriaQuery = cBuilder.createQuery(User.class);
                Root<Task> taskRoot = criteriaQuery.from(Task.class);
                Join<Task, User> taskJoin = taskRoot.join(Task_.user);
                criteriaQuery.select(taskJoin);
                criteriaQuery.groupBy(taskJoin.get(User_.id));
                criteriaQuery.orderBy(cBuilder.desc(cBuilder.count(taskRoot.get(Task_.id))));
                TypedQuery<User> taskTypedQuery = entityManager.createQuery(criteriaQuery);
                return cBuilder.equal(root.get(Task_.user), taskTypedQuery.getResultList().get(0).getId());
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
            return UserFindMaxTasksDTO.mapToUserDto(result);
        }
        else {
           throw new InputMismatchException("Incorrect status");
        }
    }

    private boolean isNameValid(String userName) {
        String regex = "[A-Za-zА-Яа-я]{3,29}";
        Pattern pattern = Pattern.compile(regex);
        if (userName == null) return false;
        if (userName.trim().length() == 0) return false;
        Matcher matcher = pattern.matcher(userName);
        return matcher.matches();
    }


}
