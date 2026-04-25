package nom.board.controller;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import nom.board.dto.BoardDTO;
import nom.board.entity.BoardEntity;
import nom.board.service.BoardService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gwaje")
public class GwajeController {
    private final BoardService boardService;

    @GetMapping("/")
    public Map<String, Object> getAll() {
        return Map.of("posts",boardService.getBoardList());
    }

    @PostMapping("/create")
    public BoardDTO create(@RequestBody BoardDTO boardDTO) {
        BoardDTO boardDTO = boardService.save(boardDTO);
        return boardDTO;
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody BoardDTO boardDTO) {
        boardDTO.setId(id);
        try {
            BoardDTO board = boardService.update(boardDTO);
            return ResponseEntity.ok(board);
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .badRequest()
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/view/{id}")
    public BoardDTO findById(@PathVariable Long id) {
        BoardDTO board = boardService.findById(id);
        return board;
    }

    @DeleteMapping("/remove/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        boardService.delete(id);       
        return ResponseEntity.noContent().build();
    }
}
