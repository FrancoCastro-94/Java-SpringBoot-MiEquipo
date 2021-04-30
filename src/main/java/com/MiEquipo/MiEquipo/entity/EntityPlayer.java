
package com.MiEquipo.MiEquipo.entity;


import java.util.Date;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class EntityPlayer{
   
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid" , strategy = "uuid")
    private String id;
    @Column
    private String email;
    @Column
    private String name;
    @Column
    private String password;
    @Column
    private String number;
    @Column
    private boolean hasTeam;
    
    
    @OneToOne
    private EntityPhoto photo;
    
    @ManyToMany
    private Set<EntityTeam> teams;

    @Temporal(TemporalType.TIMESTAMP)
    private Date alta;

    @Temporal(TemporalType.TIMESTAMP)
    private Date baja;

    
    
    //Getters and Setters

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the nombre
     */
    public String getName() {
        return name;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setName(String nombre) {
        this.name = nombre;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the numeroCamiseta
     */
    public String getNumber() {
        return number;
    }

    /**
     * @param number the numeroCamiseta to set
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * @return the foto
     */
    public EntityPhoto getPhoto() {
        return photo;
    }

    /**
     * @param photo the foto to set
     */
    public void setPhoto(EntityPhoto photo) {
        this.photo = photo;
    }

    /**
     * @return the alta
     */
    public Date getAlta() {
        return alta;
    }

    /**
     * @param alta the alta to set
     */
    public void setAlta(Date alta) {
        this.alta = alta;
    }

    /**
     * @return the baja
     */
    public Date getBaja() {
        return baja;
    }

    /**
     * @param baja the baja to set
     */
    public void setBaja(Date baja) {
        this.baja = baja;
    }

    /**
     * @return the hasTeam
     */
    public boolean hasTeam() {
        return hasTeam;
    }

    /**
     * @param hasTeam the hasTeam to set
     */
    public void setHasTeam(boolean hasTeam) {
        this.hasTeam = hasTeam;
    }

    /**
     * @return the teams
     */
    public Set<EntityTeam> getTeams() {
        return teams;
    }

    /**
     * @param teams the teams to set
     */
    public void setTeams(Set<EntityTeam> teams) {
        this.teams = teams;
    }
    
    
}

    