package uniCircle.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uniCircle.backend.dto.CircleDTO;
import uniCircle.backend.dto.CircleUserDTO;
import uniCircle.backend.dto.UserDTO;
import uniCircle.backend.entity.Circle;
import uniCircle.backend.entity.CircleUser;
import uniCircle.backend.entity.User;
import uniCircle.backend.repository.CircleRepository;
import uniCircle.backend.repository.CircleUserRepository;
import uniCircle.backend.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CircleUserService {
    private final CircleUserRepository circleUserRepository;
    private final UserRepository userRepository;
    private final CircleRepository circleRepository;


    // 유저를 동아리에 추가
    @Transactional
    public CircleUserDTO addUserToCircle(Long circleId, UserDTO userDTO) {
        Circle circle = circleRepository.findByCircleId(circleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 동아리입니다."));

        User user = userRepository.findByUserId(userDTO.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        circleUserRepository.findByCircleAndUser(circle, user)
                .ifPresent(m -> {
                    throw new IllegalArgumentException("동아리에 이미 존재하는 사용자입니다.");
                });

        CircleUser circleUser = CircleUser.builder()
                .circle(circle)
                .user(user)
                .build();

        circleUserRepository.save(circleUser);

        return CircleUserDTO.fromEntity(circleUser);
    }

    // 유저를 동아리에서 제거
    @Transactional
    public CircleUserDTO removeUserFromCircle(Long circleId, UserDTO userDTO) {
        Circle circle = circleRepository.findByCircleId(circleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 동아리입니다."));

        User user = userRepository.findByUserId(userDTO.getUserId())
                .orElseThrow(()-> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        CircleUser circleUser = circleUserRepository.findByCircleAndUser(circle, user)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 동아리에 존재하지 않습니다."));

        if (circle.getAdminUser().equals(user))
            throw new IllegalArgumentException("동아리 관리자는 내보낼 수 없습니다.");


        circleUserRepository.delete(circleUser);

        return CircleUserDTO.fromEntity(circleUser);
    }

    // 유저가 속한 동아리 목록
    @Transactional
    public List<CircleDTO> getCirclesByUser(UserDTO userDTO) {
        User user = userRepository.findByUserId(userDTO.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자입니다."));

        List<CircleUser> circleUsers = circleUserRepository.findByUser(user);

        // CircleUser 엔티티에서 Circle 정보를 추출하고 CircleDTO로 변환
        return circleUsers.stream()
                .map(circleUser -> CircleDTO.fromEntity(circleUser.getCircle()))
                .collect(Collectors.toList());
    }

    // 동아리에 속한 유저 목록
    @Transactional
    public List<UserDTO> getUsersByCircle(Long circleId) {
        Circle circle = circleRepository.findByCircleId(circleId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 동아리입니다."));

        List<CircleUser> circleUsers = circleUserRepository.findByCircle(circle);

        return circleUsers.stream()
                .map(circleUser -> UserDTO.fromEntity(circleUser.getUser()))
                .collect(Collectors.toList());

    }

}
