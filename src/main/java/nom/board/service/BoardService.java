package nom.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nom.board.dto.BoardDTO;
import nom.board.entity.BoardEntity;
import nom.board.repository.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepo;

    public BoardDTO save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepo.save(boardEntity);
        return BoardDTO.fromEntity(boardEntity);
    }

    public BoardDTO findById(Long id) {
        BoardEntity boardEntity = boardRepo.findById(id).orElseThrow();
        return BoardDTO.fromEntity(boardEntity);
    }

    public List<BoardDTO> getBoardList() {
        List<BoardDTO> boardDTOs = boardRepo.findAll().stream().map(BoardDTO::fromEntity).toList();
        return boardDTOs;
    }

    @Transactional
    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = boardRepo.findById(boardDTO.getId()).orElseThrow(()->new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
        boardEntity.update(boardDTO);
        return BoardDTO.fromEntity(boardEntity);
    }

    public void delete(Long id) {
        boardRepo.deleteById(id);
    }
}
