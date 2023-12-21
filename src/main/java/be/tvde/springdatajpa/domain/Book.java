package be.tvde.springdatajpa.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="books")
public class Book {

   @Id
   //@GeneratedValue we will provide the isbn ourselves
   private String isbn;
   private String title;
   @ManyToOne(cascade = CascadeType.ALL) //all changes you do to the author via the book, will be persisted in the database
   @JoinColumn(name = "author_id") //this is the foreign key column
   private Author author;
}