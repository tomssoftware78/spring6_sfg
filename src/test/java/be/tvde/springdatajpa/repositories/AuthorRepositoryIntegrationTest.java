package be.tvde.springdatajpa.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import be.tvde.springdatajpa.TestDataUtil;
import be.tvde.springdatajpa.domain.Author;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class AuthorRepositoryIntegrationTest {

   private AuthorRepository underTest;

   @Autowired
   public AuthorRepositoryIntegrationTest(final AuthorRepository authorRepository) {
      this.underTest = authorRepository;
   }

   @Test
   void testThatAuthorCanBeCreatedAndRecalled() {
      final Author author = TestDataUtil.createTestAuthorA();
      underTest.save(author);
      final Optional<Author> result = underTest.findById(author.getId());
      assertThat(result).isPresent();
      assertThat(result.get()).isEqualTo(author);
   }


}