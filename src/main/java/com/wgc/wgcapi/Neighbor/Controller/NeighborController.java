package com.wgc.wgcapi.Neighbor.Controller;
/*
Created on 2023/06/26 9:34 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.Annotations.RequireToken;
import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Neighbor.Service.NeighborService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/neighbor")
@RequiredArgsConstructor
@RestController
public class NeighborController {

    private final NeighborService neighborService;

    @GetMapping("/{id}")
    public ResponseDto getNeighborList(HttpServletRequest request, @PathVariable("id") Long id) {
        return neighborService.getNeighborList(request, id);
    }

    @RequireToken
    @PostMapping("/{id}")
    public ResponseDto addNeighbor(HttpServletRequest request, @PathVariable("id") Long id) {
        return neighborService.addNeighbor(request, id);
    }

    @RequireToken
    @PostMapping("/{action}/{id}")
    public ResponseDto changeNeighborStatus(HttpServletRequest request, @PathVariable("action") String action, @PathVariable("id") Long id) {
        return neighborService.changeNeighborStatus(request, action, id);
    }
}
