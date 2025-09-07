package com.he182092.mvc.tree.service;

import com.he182092.mvc.tree.model.TreeNode;
import com.he182092.mvc.tree.repository.TreeNodeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class TreeService {
    private final TreeNodeRepository repository;

    public TreeService(TreeNodeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<TreeNode> getRoots() {
        return repository.findByParentIsNull();
    }

    @Transactional(readOnly = true)
    public Optional<TreeNode> getById(Integer id) {
        return repository.findById(id);
    }

    @Transactional
    public TreeNode createRoot(String data) {
        TreeNode node = new TreeNode();
        node.setData(data);
        node.setParent(null);
        return repository.save(node);
    }

    @Transactional
    public TreeNode createChild(Integer parentId, String data) {
        TreeNode parent = repository.findById(parentId)
                .orElseThrow(() -> new NoSuchElementException("không tìm thấy: " + parentId));
        TreeNode child = new TreeNode();
        child.setData(data);
        child.setParent(parent);
        return repository.save(child);
    }

    @Transactional
    public TreeNode updateNode(Integer id, String newData, String newData2) {
        TreeNode node = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("không tìm thấy: " + id));
        node.setData(newData);
        node.setData2(newData2);
        return repository.save(node);
    }

    @Transactional
    public void deleteSubtree(Integer id) {
        TreeNode node = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Node not found: " + id));
        repository.delete(node);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getTreeAsList() {
        List<TreeNode> roots = getRoots();
        List<Map<String, Object>> list = new ArrayList<>();
        for (TreeNode root : roots) {
            list.add(convertToMap(root));
        }
        return list;
    }

    private Map<String, Object> convertToMap(TreeNode node) {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("id", node.getId());
        map.put("data", node.getData());
        if (node.getData2() != null && !node.getData2().isBlank()) {
            map.put("data2", node.getData2());
        }
        List<Map<String, Object>> children = new ArrayList<>();
        for (TreeNode child : node.getChildren()) {
            children.add(convertToMap(child));
        }
        map.put("children", children);
        return map;
    }
}


