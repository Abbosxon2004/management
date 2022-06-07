package uz.pdp.online.management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripodTourniquet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne

    private Employees employees;


    private Timestamp entranceTime;


    private Timestamp exitTime;

    @Column(updatable = false)
    private Date date;

}
