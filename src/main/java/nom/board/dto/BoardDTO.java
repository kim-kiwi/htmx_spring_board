package nom.board.dto;

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

    public static BoardDTO fromEntity(BoardEntity boardEntity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setContents(boardEntity.getContents());
        boardDTO.setTitle(boardEntity.getTitle());
        return boardDTO;
    }
}