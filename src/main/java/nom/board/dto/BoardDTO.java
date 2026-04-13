package nom.board.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import nom.board.entity.BoardEntity;

@Getter
@Setter
@ToString
public class BoardDTO {
    private Long id;
    private String title;
    private String contents;
    private String createdAt;

    private String dateFormat(LocalDateTime date) {
        if (date==null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public static BoardDTO toBoardDTO(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setContents(boardEntity.getContents());
        boardDTO.setTitle(boardEntity.getTitle());
        boardDTO.setCreatedAt(boardDTO.dateFormat(boardEntity.getCreatedAt()));
        return boardDTO;
    }
}