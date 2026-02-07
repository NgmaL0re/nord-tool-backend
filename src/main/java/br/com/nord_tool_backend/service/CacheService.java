package br.com.nord_tool_backend.service;

public interface CacheService {
    void limparCache(String cacheName);
    void limparTodos();
}
