package com.he182092.mvc.tree.controller;

import com.he182092.mvc.tree.model.TreeNode;
import com.he182092.mvc.tree.service.TreeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tree")
public class TreeRestController {
    private final TreeService treeService;

    public TreeRestController(TreeService treeService) {
        this.treeService = treeService;
    }

    @GetMapping
    public List<Map<String, Object>> getTree() {
        return treeService.getTreeAsList();
    }

    @PostMapping("/root")
    public ResponseEntity<TreeNode> createRoot(@RequestBody Map<String, String> body) {
        String data = body.get("data");
        TreeNode created = treeService.createRoot(data);
        return ResponseEntity.created(URI.create("/api/tree/" + created.getId())).body(created);
    }

    @PostMapping("/child")
    public ResponseEntity<TreeNode> createChild(@RequestBody Map<String, String> body) {
        Integer parentId = Integer.valueOf(body.get("parentId"));
        String data = body.get("data");
        TreeNode created = treeService.createChild(parentId, data);
        return ResponseEntity.created(URI.create("/api/tree/" + created.getId())).body(created);
    }

    @PutMapping("/{id}")
    public TreeNode update(@PathVariable Integer id, @RequestBody Map<String, String> body) {
        String data = body.get("data");
        String data2 = body.get("data2");
        return treeService.updateNode(id, data, data2);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        treeService.deleteSubtree(id);
        return ResponseEntity.noContent().build();
    }
}


