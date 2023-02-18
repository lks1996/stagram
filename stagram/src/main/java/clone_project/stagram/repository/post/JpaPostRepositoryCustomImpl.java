package clone_project.stagram.repository.post;

import javax.persistence.EntityManager;

public class JpaPostRepositoryCustomImpl implements JpaPostRepositoryCustom {

    private final EntityManager em;

    public JpaPostRepositoryCustomImpl(EntityManager em) {
        this.em = em;
    }
}
