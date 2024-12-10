package vk.itmo.teamgray.backend.link.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.link.dto.LinkCreateDto;
import vk.itmo.teamgray.backend.link.dto.LinkDto;
import vk.itmo.teamgray.backend.link.dto.LinkUpdateDto;
import vk.itmo.teamgray.backend.link.entities.Link;
import vk.itmo.teamgray.backend.link.mapper.LinkMapper;
import vk.itmo.teamgray.backend.link.repos.LinkRepository;
import vk.itmo.teamgray.backend.resume.services.ResumeService;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService {
    private final LinkRepository linkRepository;
    private final ResumeService resumeService;
    private final LinkMapper linkMapper;

    public Link findEntityById(Long id) {
        return linkRepository.findById(id).orElseThrow(ModelNotFoundException::new);
    }

    public LinkDto findById(Long id) {
        return linkMapper.toDto(findEntityById(id));
    }

    public LinkDto createLink(LinkCreateDto data) {
        return createLink(data, true);
    }

    public LinkDto createLink(LinkCreateDto data, boolean persist) {
        var resume = resumeService.findEntityById(data.getResumeId());

        var link = new Link(data, resume);

        if (persist) {
            link = linkRepository.save(link);
        }

        return linkMapper.toDto(link);
    }

    public LinkDto updateLink(LinkUpdateDto data) {
        return linkMapper.toDto(linkRepository.save(new Link(
                data,
                resumeService.findEntityById(data.getResumeId())
            ))
        );
    }

    public void deleteById(Long id) {
        linkRepository.deleteById(id);
    }
}
