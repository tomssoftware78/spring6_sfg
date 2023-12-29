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
import be.tvde.springdatajpa.domain.Book;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryIntegrationTest {

   private BookRepository underTest;

   @Autowired
   public BookRepositoryIntegrationTest(final BookRepository underTest) {
      this.underTest = underTest;
   }

   @Test
   void testThatBookCanBeCreatedAndRecalled() {
      final Author author = TestDataUtil.createTestAuthorA();
      final Book bookA = TestDataUtil.createTestBookA();
      bookA.setAuthor(author);
      underTest.save(bookA);
      final Optional<Book> book = underTest.findById(bookA.getIsbn());
      assertThat(book).isPresent();
      assertThat(book.get()).isEqualTo(bookA);
   }
}
