package org.zerock.myapp.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;


@Data

@Entity(name = "Team")
@Table(name="team")
public class Team implements Serializable { // Parent, One(1)
	@Serial private static final long serialVersionUID = 1L;

	// 1. Set PK
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "team_id")
	private Long id;				// PK
	
	
	// 2. 일반속성들
	@Basic(optional = false)		// Not Null Contraint
	private String name;
	
	
	// ==========================================
	// ManyToOne (N:1), Bi-directional Association
	// ==========================================
	
	// ????
	
	/**
	 * ------------------------------- 
	 * Important: Set the owner of an Bi-directional association. 
	 * ------------------------------- 
	 * (1) In *Uni-directional association, do *Not set the owner of this association. (***) 
	 * (2) In only Bi-directional association, set the *Owner of this association 
	 *     with `mappedBy` element in the (1) @OneToOne (2) @OneToMany (3) @ManyToMany annotations. 
	 * (3) There is *No `mappedBy` element in the @ManyToOne annotation
	 * 	   Because Many (Child) always have an FK columm, thus no `mappedBy` element.
	 * (***) -------------------------------
	 */
//	@OneToMany(mappedBy = "myTeam")
//	@OneToMany(
//			mappedBy = "myTeam", 
//			targetEntity = Member.class, 
//			fetch = FetchType.LAZY)
//	@OneToMany(
//			mappedBy = "myTeam", 
//			targetEntity = Member.class,
//			cascade = CascadeType.ALL)
	@OneToMany(mappedBy = "myTeam", targetEntity = Member.class)
	@ToString.Exclude
	private List<Member> members = new ArrayList<>();	//Set Children
	
	
   
} // end class

