package com.example.traditionalmarket.service;

import com.example.traditionalmarket.dto.request.board.CreateBoardDto;
import com.example.traditionalmarket.dto.request.board.UpdateBoardDto;
import com.example.traditionalmarket.dto.response.board.BoardByIdResponseData;
import com.example.traditionalmarket.dto.response.board.BoardResponseData;
import com.example.traditionalmarket.entity.Board;
import com.example.traditionalmarket.entity.Market;
import com.example.traditionalmarket.entity.Reaction;
import com.example.traditionalmarket.entity.User;
import com.example.traditionalmarket.exception.NotFoundException;
import com.example.traditionalmarket.exception.errorcode.ErrorCode;
import com.example.traditionalmarket.repository.BoardRepository;
import com.example.traditionalmarket.repository.MarketRepository;
import com.example.traditionalmarket.repository.ReactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReactionRepository reactionRepository;
    private final MarketRepository marketRepository;

    // 시장별 게시글 전체 조회하기
    public BoardResponseData getAllBoards(String marketName) {
        List<Board> boards = boardRepository.findAllByMarketNameOrderByCreatedAtDesc(marketName);
        return BoardResponseData.of(marketName, boards);
    }

    // 게시글 상세 조회하기
    public BoardByIdResponseData getBoardById(User user, UUID boardId) {
        Board board = findBoard(boardId);
        Reaction reaction = reactionRepository.findByUserAndBoard(user, board).orElse(null);

        return BoardByIdResponseData.of(board, reaction);
    }


    // 게시글 작성하기
    public void createBoard(User user, String marketName, CreateBoardDto createBoardDto) {
        Market market = marketRepository.findByName(marketName).orElseThrow(
                () -> new NotFoundException(ErrorCode.MARKET_NAME_NOT_FOUND));

        Board board = Board.builder()
                .title(createBoardDto.getTitle())
                .content(createBoardDto.getContent())
                .user(user)
                .likeCount(0)
                .disLikeCount(0)
                .market(market)
                .build();
        boardRepository.save(board);
    }


    // 게시글 수정하기
    public void updateBoard(User user, UUID boardId, UpdateBoardDto updateBoardDto) {
        Board newBoard = updateFindBoardByIdAndUser(user, boardId);
        newBoard.setTitle(updateBoardDto.getTitle())
                .setContent(updateBoardDto.getContent());
        boardRepository.save(newBoard);
    }

    // 게시글 삭제하기
    public void deleteBoard(User user, UUID boardId) {
        Board board = deleteFindBoardByIdAndUser(user, boardId);
        boardRepository.delete(board);
    }


    private Board findBoard(UUID boardId) {
        return boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
    }

    private Board updateFindBoardByIdAndUser(User user, UUID boardId) {
        return boardRepository.findByIdAndUser(boardId, user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.BOARD_NOT_FOUND));
    }

    private Board deleteFindBoardByIdAndUser(User user, UUID boardId) {
        return boardRepository.findByIdAndUser(boardId, user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NO_ACCESS));
    }
}
