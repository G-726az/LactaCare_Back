package ista.M4A2.config.authenticator;
import jakarta.persistence.*;
import java.time.LocalDateTime;

import ista.M4A2.models.entity.PersonaEmpleado;
import ista.M4A2.models.entity.Roles;


//En caso de querer mover la clase mover a "ENTITY"
@Entity
@Table(name = "credential_codes")
public class CredentialCode {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    
	    @Column(unique = true, nullable = false, length = 50)
	    private String code;
	    
	    @ManyToOne
	    @JoinColumn(name = "role_id", nullable = false)
	    private Roles rol;
	    
	    @Column(name = "is_active")
	    private Boolean isActive = true;
	    
	    @Column(name = "max_uses")
	    private Integer maxUses;
	    
	    @Column(name = "current_uses")
	    private Integer currentUses = 0;
	    
	    @Column(name = "created_at")
	    private LocalDateTime createdAt;
	    
	    @Column(name = "expires_at")
	    private LocalDateTime expiresAt;
	    
	    @ManyToOne
	    @JoinColumn(name = "created_by")
	    private PersonaEmpleado createdBy;
	    
	    @PrePersist
	    protected void onCreate() {
	        createdAt = LocalDateTime.now();
	    }
	    // Constructores
	    public CredentialCode() {
	    }
	    
	    public CredentialCode(String code, Roles rol, Boolean isActive) {
	        this.code = code;
	        this.rol = rol;
	        this.isActive = isActive;
	    }
	 // Getters y Setters
	    public Integer getId() {
	        return id;
	    }
	    
	    public void setId(Integer id) {
	        this.id = id;
	    }
	    
	    public String getCode() {
	        return code;
	    }
	    
	    public void setCode(String code) {
	        this.code = code;
	    }
	    
	    public Roles getRol() {
	        return rol;
	    }
	    
	    public void setRol(Roles rol) {
	        this.rol = rol;
	    }
	    
	    public Boolean getIsActive() {
	        return isActive;
	    }
	    
	    public void setIsActive(Boolean isActive) {
	        this.isActive = isActive;
	    }
	    
	    public Integer getMaxUses() {
	        return maxUses;
	    }
	    
	    public void setMaxUses(Integer maxUses) {
	        this.maxUses = maxUses;
	    }
	    
	    public Integer getCurrentUses() {
	        return currentUses;
	    }
	    
	    public void setCurrentUses(Integer currentUses) {
	        this.currentUses = currentUses;
	    }
	    
	    public LocalDateTime getCreatedAt() {
	        return createdAt;
	    }
	    
	    public void setCreatedAt(LocalDateTime createdAt) {
	        this.createdAt = createdAt;
	    }
	    
	    public LocalDateTime getExpiresAt() {
	        return expiresAt;
	    }
	    
	    public void setExpiresAt(LocalDateTime expiresAt) {
	        this.expiresAt = expiresAt;
	    }
	    
	    public PersonaEmpleado getCreatedBy() {
	        return createdBy;
	    }
	    
	    public void setCreatedBy(PersonaEmpleado createdBy) {
	        this.createdBy = createdBy;
	    }
	}
