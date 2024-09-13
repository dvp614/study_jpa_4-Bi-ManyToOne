package org.zerock.myapp.domain;

import java.io.Serial;
import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data

@Slf4j

@Entity(name = "Member")
@Table(name = "member")
public class Member implements Serializable { // Child, (N)
	@Serial
	private static final long serialVersionUID = 1L;

// 1. Set PK
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; // PK

	// 2. 일반속성들
	@Basic(optional = true) // Not Null Constraint
	private String name;

	// ==========================================
	// ManyToOne (N:1), Bi-directional Association
	// ==========================================

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

	@ManyToOne(optional = true, targetEntity = Team.class)
   /**
    * -------------------------------
    * @JoinColumn
    * -------------------------------
    * (1) Set the FK column name of Many (Child) table. (***)
    * (2) If @JoinColumn annotation abbreviated,
    *       The default FK column name = 
    *          The name of the entity + "_" + The name of the referenced primary key column.
    * (3) @JoinColumn(table): The name of the table that contains the FK column.
    *       If `table` propery *Not specified,
    *       The FK column is assumed to be in the primary table of the applicable entity. 
    * -------------------------------
    */
	@JoinColumn(
			name = "my_team", 
			table = "member", 
			referencedColumnName = "team_id")

	private Team myTeam; // Set FK
	
	public void setMyTeam(Team myTeam) {
		log.trace("setMyTeam({}) invoked.", myTeam);
		
		Team oldMyTeam = this.getMyTeam();
		if(oldMyTeam != null) {
			// 이전 팀에서 현재의 멤버를 제거하고,
			 boolean isRemoved = oldMyTeam.getMembers().remove(this);
			 log.info("\t+ isRemoved : {}", isRemoved);
		} // if
		
		// 새롭게 매개변수로 전달된 새로운 팀의 멤버로 참여시켜야 함
		if(myTeam != null) {
			this.myTeam = myTeam;	// PK 속성필드에 값 설정
			this.myTeam.getMembers().add(this);
		} // if
	} // setMyTeam

} // end class
