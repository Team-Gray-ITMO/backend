package vk.itmo.teamgray.backend.resume.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exceptions.ModelNotFoundException;
import vk.itmo.teamgray.backend.resume.dto.LinkCreateDto;
import vk.itmo.teamgray.backend.resume.dto.LinkDto;
import vk.itmo.teamgray.backend.resume.dto.LinkUpdateDto;
import vk.itmo.teamgray.backend.resume.entities.Link;
import vk.itmo.teamgray.backend.resume.mapper.LinkMapper;
import vk.itmo.teamgray.backend.resume.repos.LinkRepository;

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
        var resume = resumeService.findEntityById(data.resumeId());

        var link = new Link(data, resume);

        if (persist) {
            link = linkRepository.save(link);
        }

        return linkMapper.toDto(link);
    }

    public LinkDto updateLink(LinkUpdateDto data) {
        return linkMapper.toDto(linkRepository.save(new Link(
                data,
                resumeService.findEntityById(data.resumeId())
            ))
        );
    }

    public void deleteById(Long id) {
        linkRepository.deleteById(id);
    }
}
