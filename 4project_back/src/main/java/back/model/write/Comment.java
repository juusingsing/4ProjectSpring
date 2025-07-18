package back.model.write;


import back.model.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  // 기본 생성자 자동 생성
@AllArgsConstructor  // 모든 필드를 매개변수로 하는 생성사 자동 생성
@EqualsAndHashCode(callSuper = true)
public class Comment extends Model {
    private int commentsId;         // 댓글 고유 ID
    private int writingId;           // 게시글 ID
    private int parentCommentsId; // 부모 댓글 ID (대댓글)
    private String content;        // 댓글 내용 
 
}
