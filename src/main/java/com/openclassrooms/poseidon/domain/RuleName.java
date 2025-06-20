package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "rulename")
@AllArgsConstructor
@NoArgsConstructor
public class RuleName implements DomainEntity<RuleName> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String description;

    private String json;

    @Column(length = 512)
    private String template;

    private String sqlStr;

    private String sqlPart;

    @Override
    public void update(RuleName ruleName) {
        this.name = ruleName.getName();
        this.description = ruleName.getDescription();
        this.json = ruleName.getJson();
        this.template = ruleName.getTemplate();
        this.sqlStr = ruleName.getSqlStr();
        this.sqlPart = ruleName.getSqlPart();
    }
}
