package com.openclassrooms.poseidon.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "Rulename")
@AllArgsConstructor
@NoArgsConstructor
public class RuleName implements DomainEntity<RuleName> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String json;

    @Column(length = 512)
    @NotBlank
    private String template;

    @Column(name = "sqlStr")
    @NotBlank
    private String sql;

    @NotBlank
    private String sqlPart;

    public RuleName(String name, String description, String json, String template, String sql, String sqlPart) {
        this.name = name;
        this.description = description;
        this.json = json;
        this.template = template;
        this.sql = sql;
        this.sqlPart = sqlPart;
    }

    @Override
    public void update(RuleName ruleName) {
        this.name = ruleName.getName();
        this.description = ruleName.getDescription();
        this.json = ruleName.getJson();
        this.template = ruleName.getTemplate();
        this.sql = ruleName.getSql();
        this.sqlPart = ruleName.getSqlPart();
    }
}
