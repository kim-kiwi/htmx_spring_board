package nom.board.controller;

import java.util.List;
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

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;    

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/list")
    public String boardList() {
        return "list";
    }

    @GetMapping("/api/list")
    @ResponseBody
    public String test(@RequestParam(name="page", defaultValue = "0") int page) {
        return boardService.getBoardListHTML(page);
    }

    @PostMapping("/save")
    public ResponseEntity<Void> save(BoardDTO boardDTO) {
        boardService.save(boardDTO);
        return ResponseEntity
            .status(200)
            .header("HX-Redirect", "/list")
            .build();
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board",boardDTO);
        return "update";
    }

    @PutMapping("/board/{id}")
    public String update(@PathVariable Long id, BoardDTO boardDTO, Model model) {
        try {
            boardService.update(boardDTO);
            BoardDTO board = boardService.findById(id);
            model.addAttribute("board",board);
            return "details";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage",e.getMessage());
            model.addAttribute("board",boardDTO);
            return "update";
        }
    }

    @GetMapping("/board/{id}")
    public String findById(@PathVariable Long id, Model model) {
        BoardDTO board = boardService.findById(id);
        model.addAttribute("board",board);
        return "details";
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            boardService.delete(id);       
            return ResponseEntity
                .status(200)
                .header("HX-Redirect", "/list")
                .build();
        } catch(NoSuchElementException e) {
            return ResponseEntity
                .status(500)
                .header("HX-Redirect", "/list")
                .build();
        }
    }
}