package com.wgc.wgcapi.Neighbor.Service;
/*
Created on 2023/06/26 9:36 PM In Intelli J IDEA 
by jeon-wangi
*/

import com.wgc.wgcapi.Common.DTO.ResponseDto;
import com.wgc.wgcapi.Member.Entity.Member;
import com.wgc.wgcapi.Member.Service.MemberService;
import com.wgc.wgcapi.Neighbor.DTO.NeighborDto;
import com.wgc.wgcapi.Neighbor.Entity.Neighbor;
import com.wgc.wgcapi.Neighbor.Enums.NeighborStatus;
import com.wgc.wgcapi.Neighbor.Repository.NeighborDataRepository;
import com.wgc.wgcapi.Neighbor.Repository.NeighborRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NeighborService {

    private final NeighborDataRepository neighborDataRepository;
    private final MemberService memberService;
    private final NeighborRepository repository;

    public ResponseDto getNeighborList(HttpServletRequest request, Long id) {
        List<NeighborDto> list = repository.getNeighborList(id);
        return new ResponseDto(list);
    }

    public ResponseDto addNeighbor(HttpServletRequest request, Long id) {
        Member requester = memberService.getMemberInfo(request);
        Member accepter = memberService.findById(id);
        if (Objects.isNull(accepter))
            return new ResponseDto(HttpStatus.NOT_FOUND, "Member id " + id + " is not found !");

        Neighbor neighborRequest = repository.getNeighborByRequestUserId(requester.getId(), id);
        if (Objects.nonNull(neighborRequest))
            return new ResponseDto(HttpStatus.NOT_MODIFIED, "Request Already Exists !");

        neighborDataRepository.save(new Neighbor(requester, accepter));
        return new ResponseDto(HttpStatus.CREATED, "Request success !");
    }

    public ResponseDto changeNeighborStatus(HttpServletRequest request, String action, Long id) {
        Member member = memberService.getMemberInfo(request);
        NeighborStatus status = getNeighborStatusByString(action);

        if (status.equals(NeighborStatus.DELETE)) {
            Neighbor neighbor = repository.getNeighbor(member.getId(), id);
            neighbor.deleteNeighbor();
            return new ResponseDto(HttpStatus.ACCEPTED);
        }
        Neighbor neighbor = repository.getNeighborStatus(member.getId(), id);
        if (Objects.isNull(neighbor))
            return new ResponseDto(HttpStatus.NOT_FOUND, "Neighbor request Not Found !");

        if(neighbor.getIsAccept() == 'Y')
            return new ResponseDto(HttpStatus.NOT_MODIFIED, "already accepted !");

        neighbor.updateRequestStatus(status);

        return new ResponseDto(HttpStatus.ACCEPTED);
    }

    private NeighborStatus getNeighborStatusByString(String action) {
        switch (action) {
            case "accept":
                return NeighborStatus.ACCEPT;
            case "refuse":
                return NeighborStatus.REFUSE;
            case "hold":
                return NeighborStatus.HOLD;
            case "remove":
                return NeighborStatus.DELETE;
            default:
                throw new IllegalArgumentException("Unknown status type for " + action);
        }
    }
}
