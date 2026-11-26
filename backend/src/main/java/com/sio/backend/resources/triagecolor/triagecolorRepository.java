package com.sio.backend.resources.triagecolor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface triagecolorRepository extends JpaRepository<triagecolor, String> {
    public triagecolor findByCode(String Code);
}
