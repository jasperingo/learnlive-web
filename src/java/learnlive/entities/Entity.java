
package learnlive.entities;

import java.time.LocalDateTime;


abstract public class Entity {
    
    protected long id;
    
    protected LocalDateTime createdAt;
    
    
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}

