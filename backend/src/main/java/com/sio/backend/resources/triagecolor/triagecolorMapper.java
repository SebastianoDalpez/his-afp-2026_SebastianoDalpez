package com.sio.backend.resources.triagecolor;

import com.sio.backend.resources.triagecolor.triagecolor;
import org.springframework.stereotype.Component;

@Component
public class triagecolorMapper {
    public triagecolor toEntity(triagecolorDTO dto){
        return new triagecolor(
            dto.code(), 
            dto.displayName(), 
            dto.priority(), 
            dto.hexValue()
        );
    }
    
    public triagecolorDTO toDTO(triagecolor Entity){
        return new triagecolorDTO(
            Entity.getCode(), 
            Entity.getDisplayName(), 
            Entity.getPriority(),  
            Entity.getHexValue()
        );
    }
}
