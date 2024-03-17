package in.upcode.cat.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class QuestionMapperTest {

    private QuestionMapper questionMapper;

    @BeforeEach
    public void setUp() {
        questionMapper = new QuestionMapperImpl();
    }
}
