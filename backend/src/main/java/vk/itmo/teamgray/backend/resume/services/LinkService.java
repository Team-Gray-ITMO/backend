package vk.itmo.teamgray.backend.resume.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.resume.dto.LinkCreateDto;
import vk.itmo.teamgray.backend.resume.dto.LinkUpdateDto;
import vk.itmo.teamgray.backend.resume.entities.Link;
import vk.itmo.teamgray.backend.resume.repos.LinkRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {
    private final LinkRepository linkRepository;
    private final ResumeService resumeService;

    public Link findById(Long id) {
        return linkRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public Link createLink(LinkCreateDto data) {
        return linkRepository.save(new Link(
            data,
            resumeService.findById(data.resumeId())
        ));
    }

    public Link updateLink(LinkUpdateDto data) {
        return linkRepository.save(new Link(
            data,
            resumeService.findById(data.resumeId())
        ));
    }

    public void deleteById(Long id) {
        linkRepository.deleteById(id);
    }
}
