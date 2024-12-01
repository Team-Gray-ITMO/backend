package vk.itmo.teamgray.backend.resume.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import vk.itmo.teamgray.backend.resume.dto.ResumeDto;
import vk.itmo.teamgray.backend.resume.mapper.ResumeMapper;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

import java.util.List;

@Controller
@RequestMapping("/resumes")
@RequiredArgsConstructor
public class ResumeController {
    private final ResumeService resumeService;
    private final ResumeMapper resumeMapper;

    @GetMapping()
    public ResponseEntity<List<ResumeDto>> getMyResumes(){
        return ResponseEntity.ok(resumeMapper.toDtoList(resumeService.findAll()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResumeDto> findById(@PathVariable Long id){
        return ResponseEntity.ok(resumeService.getById(id));
    }
}
