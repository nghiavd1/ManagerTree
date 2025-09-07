package com.he182092.mvc.tree.repository;

import com.he182092.mvc.tree.model.TreeNode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TreeNodeRepository extends JpaRepository<TreeNode, Integer> {
    List<TreeNode> findByParentIsNull();
}


