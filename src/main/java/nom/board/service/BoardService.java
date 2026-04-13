package nom.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import nom.board.dto.BoardDTO;
import nom.board.entity.BoardEntity;
import nom.board.repository.BoardRepository;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepo;

    public void save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepo.save(boardEntity);
    }

    public BoardDTO findById(Long id) {
        BoardEntity boardEntity = boardRepo.findById(id).orElseThrow();
        return BoardDTO.toBoardDTO(boardEntity);
    }

    public String getBoardListHTML(int page) {
        Pageable pageable = PageRequest.of(page,5, Sort.by("createdAt").descending());
        Page<BoardEntity> resultPage = boardRepo.findAll(pageable);
        List<BoardDTO> boardDTOs = resultPage.getContent().stream().map(BoardDTO::toBoardDTO).toList();
        String result = "";
        for (BoardDTO boardEntity: boardDTOs) { 
            result+=String.format("<li hx-on:click=\"window.location.href='/board/%d'\">",boardEntity.getId());
            result+="<fieldset>";
            result+=String.format("<legend>%s</legend>",boardEntity.getTitle());
            result+="<pre>";
            result+=boardEntity.getContents();
            result+="</pre>";
            result+="<small>";
            result+=boardEntity.getCreatedAt();
            result+="</small>";
            result+="</fieldset>";
            result+="</li>";
        }
        if (resultPage.hasNext()) {
            result+=String.format("<div hx-get=\"/api/list?page=%d\" hx-trigger=\"revealed\" hx-swap=\"outerHTML\"></div>",page+1);
        }
        return result;
    }

    @Transactional
    public void update(BoardDTO boardDTO) {
        BoardEntity boardEntity = boardRepo.findById(boardDTO.getId()).orElseThrow(()->new IllegalArgumentException("해당 게시글을 찾을 수 없습니다."));
        boardEntity.update(boardDTO);
    }

    public void delete(Long id) {
        boardRepo.deleteById(id);
    }
}
