package vk.itmo.teamgray.backend.link.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vk.itmo.teamgray.backend.common.exception.DataNotFoundException;
import vk.itmo.teamgray.backend.common.service.BaseService;
import vk.itmo.teamgray.backend.link.dto.LinkCreateDto;
import vk.itmo.teamgray.backend.link.dto.LinkDto;
import vk.itmo.teamgray.backend.link.dto.LinkUpdateDto;
import vk.itmo.teamgray.backend.link.entities.Link;
import vk.itmo.teamgray.backend.link.mapper.LinkMapper;
import vk.itmo.teamgray.backend.link.repos.LinkRepository;
import vk.itmo.teamgray.backend.resume.services.ResumeService;
import vk.itmo.teamgray.backend.user.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional
public class LinkService extends BaseService<Link> {
    private final LinkRepository linkRepository;
    private final ResumeService resumeService;
    private final LinkMapper linkMapper;
    private final UserService userService;

    @Override
    public Link findEntityById(Long id) {
        return linkRepository.findByIdSecure(id, userService.getAuthUser().getId())
            .orElseThrow(() -> DataNotFoundException.entity(Link.class, id));
    }

    public LinkDto findById(Long id) {
        return linkMapper.toDto(findEntityById(id));
    }

    public LinkDto createLink(LinkCreateDto data) {
        var resume = resumeService.findEntityById(data.getResumeId());

        var link = new Link(data, resume);

        link = linkRepository.save(link);

        return linkMapper.toDto(link);
    }

    public LinkDto updateLink(LinkUpdateDto updateDto) {
        var link = findEntityById(updateDto.getId());

        boolean updated = false;

        updated |= updateIfPresent(updateDto.getPlatformName(), link::setPlatformName);
        updated |= updateIfPresent(updateDto.getProfileUrl(), link::setProfileUrl);

        updated |= resumeService.updateLinkToEntityIfPresent(updateDto.getResumeId(), link::setResume);

        if (updated) {
            link = linkRepository.save(link);
        }

        return linkMapper.toDto(link);
    }

    public void deleteById(Long id) {
        linkRepository.deleteById(id);
    }
}
