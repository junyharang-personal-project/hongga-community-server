package org.comunity.hongga.repository.manual.comment;

import org.comunity.hongga.model.entity.manual.comment.ManualComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManualCommentRepository extends JpaRepository<ManualComment, Long> {
} // interface 끝
