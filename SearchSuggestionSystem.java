import java.util.*;

class TrieNode {
    Map<Character, TrieNode> children;
    boolean isEndOfWord;

    public TrieNode() {
        children = new HashMap<>();
        isEndOfWord = false;
    }
}

class Trie {
    private TrieNode root;

    public Trie() {
        root = new TrieNode();
    }

    public void insert(String word) {
        TrieNode node = root;
        for (char ch : word.toCharArray()) {
            node.children.putIfAbsent(ch, new TrieNode());
            node = node.children.get(ch);
        }
        node.isEndOfWord = true;
    }

    public List<String> search(String prefix) {
        TrieNode node = root;
        for (char ch : prefix.toCharArray()) {
            if (!node.children.containsKey(ch)) { 
                return new ArrayList<>();
            }
            node = node.children.get(ch);
        }
        return getAllWordsWithPrefix(node, prefix);
    }

    private List<String> getAllWordsWithPrefix(TrieNode node, String prefix) {
        List<String> suggestions = new ArrayList<>();
        if (node.isEndOfWord) {
            suggestions.add(prefix);
        }
        for (char ch : node.children.keySet()) {
            suggestions.addAll(getAllWordsWithPrefix(node.children.get(ch), prefix + ch));
        }
        return suggestions;
    }
}

public class SearchSuggestionSystem {
    public static Map<String, List<String>> buildSearchSuggestions(String[] products, String[] queries) {
        Trie trie = new Trie();
        for (String product : products) {
            trie.insert(product);
        }

        Map<String, List<String>> suggestions = new HashMap<>();
        for (String query : queries) {
            suggestions.put(query, trie.search(query));
        }
        return suggestions;
    }

    public static void main(String[] args) {
        String[] products = {"mobile", "mouse", "moneypot", "monitor", "mousepad","monkey"};
        String[] queries = {"m", "mon", "t", "mouse"};



        Map<String, List<String>> suggestions = buildSearchSuggestions(products, queries);
        for (String query : suggestions.keySet()) {
            System.out.println(suggestions.get(query));
        }
      
    }

    
}
