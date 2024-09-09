import java.util.*;

interface Cache {
    String get(String key);
    void put(String key, String value);
    void displayCache();
}

class LRUCache implements Cache {
    private int capacity;
    private LinkedHashMap<String, String> cacheMap;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cacheMap = new LinkedHashMap<>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
                return size() > LRUCache.this.capacity;
            }
        };
    }

    public String get(String key) {
        return cacheMap.getOrDefault(key, null);
    }

    public void put(String key, String value) {
        cacheMap.put(key, value);
    }

    public void displayCache() {
        System.out.println("LRU Cache: " + cacheMap);
    }
}

class LFUCache implements Cache {
    private int capacity;
    private Map<String, String> cacheMap;
    private Map<String, Integer> frequencyMap;
    private LinkedHashMap<String, Long> timestampMap;

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.cacheMap = new HashMap<>();
        this.frequencyMap = new HashMap<>();
        this.timestampMap = new LinkedHashMap<>();
    }

    public String get(String key) {
        if (!cacheMap.containsKey(key)) return null;
        updateAccess(key);
        return cacheMap.get(key);
    }

    public void put(String key, String value) {
        if (capacity <= 0) return;
        if (cacheMap.containsKey(key)) {
            cacheMap.put(key, value);
            updateAccess(key);
        } else {
            if (cacheMap.size() >= capacity) evictLFU();
            cacheMap.put(key, value);
            frequencyMap.put(key, 1);
            timestampMap.put(key, System.nanoTime());
        }
    }

    private void evictLFU() {
        String lfuKey = null;
        int minFrequency = Integer.MAX_VALUE;
        long oldestTimestamp = Long.MAX_VALUE;

        for (String key : cacheMap.keySet()) {
            int freq = frequencyMap.get(key);
            long timestamp = timestampMap.get(key);
            if (freq < minFrequency || (freq == minFrequency && timestamp < oldestTimestamp)) {
                minFrequency = freq;
                oldestTimestamp = timestamp;
                lfuKey = key;
            }
        }
        if (lfuKey != null) {
            cacheMap.remove(lfuKey);
            frequencyMap.remove(lfuKey);
            timestampMap.remove(lfuKey);
        }
    }

    private void updateAccess(String key) {
        frequencyMap.put(key, frequencyMap.getOrDefault(key, 0) + 1);
        timestampMap.put(key, System.nanoTime());
    }

    public void displayCache() {
        System.out.println("LFU Cache: " + cacheMap);
    }
}

class MultiLevelCacheSystem {
    private List<Cache> cacheLevels;

    public MultiLevelCacheSystem() {
        cacheLevels = new ArrayList<>();
    }

    public void addCacheLevel(int size, String evictionPolicy) {
        Cache cache;
        if (evictionPolicy.equalsIgnoreCase("LRU")) {
            cache = new LRUCache(size);
        } else if (evictionPolicy.equalsIgnoreCase("LFU")) {
            cache = new LFUCache(size);
        } else {
            System.out.println("Invalid eviction policy.");
            return;
        }
        cacheLevels.add(cache);
        System.out.println("Cache level added with size: " + size + " and eviction policy: " + evictionPolicy);
    }

    public void removeCacheLevel(int level) {
        if (level >= 1 && level <= cacheLevels.size()) {
            cacheLevels.remove(level - 1);
            System.out.println("Cache level " + level + " removed.");
        } else {
            System.out.println("Invalid cache level.");
        }
    }

    public String get(String key) {
        for (int i = 0; i < cacheLevels.size(); i++) {
            String value = cacheLevels.get(i).get(key);
            if (value != null) {
                System.out.println("Cache hit at level " + (i + 1));
                promoteData(key, value, i);
                return value;
            }
        }
        System.out.println("Cache miss.");
        return null;
    }

    public void put(String key, String value) {
        if (cacheLevels.isEmpty()) {
            System.out.println("No cache levels available.");
            return;
        }

        // Insert into all cache levels simultaneously
        for (Cache cache : cacheLevels) {
            cache.put(key, value);
        }
        System.out.println("Inserted key: " + key + " into all cache levels.");
    }

    private void promoteData(String key, String value, int foundAtLevel) {
        for (int i = foundAtLevel; i > 0; i--) {
            cacheLevels.get(i).put(key, value); // Move the data to the higher cache levels
        }
    }

    public void displayCache() {
        for (int i = 0; i < cacheLevels.size(); i++) {
            System.out.print("Level " + (i + 1) + ": ");
            cacheLevels.get(i).displayCache();
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MultiLevelCacheSystem cacheSystem = new MultiLevelCacheSystem();

        while (true) {
            System.out.println("\nOptions:");
            System.out.println("1. Add Cache Level");
            System.out.println("2. Remove Cache Level");
            System.out.println("3. Put Key-Value in Cache");
            System.out.println("4. Get Value by Key");
            System.out.println("5. Display Cache");
            System.out.println("6. Exit");

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter cache size: ");
                    int size = scanner.nextInt();
                    System.out.print("Enter eviction policy (LRU/LFU): ");
                    String evictionPolicy = scanner.next();
                    cacheSystem.addCacheLevel(size, evictionPolicy);
                    break;
                case 2:
                    System.out.print("Enter cache level to remove: ");
                    int level = scanner.nextInt();
                    cacheSystem.removeCacheLevel(level);
                    break;
                case 3:
                    System.out.print("Enter key: ");
                    String key = scanner.next();
                    System.out.print("Enter value: ");
                    String value = scanner.next();
                    cacheSystem.put(key, value);
                    break;
                case 4:
                    System.out.print("Enter key to retrieve: ");
                    key = scanner.next();
                    String result = cacheSystem.get(key);
                    if (result != null) {
                        System.out.println("Value: " + result);
                    } else {
                        System.out.println("Key not found in cache.");
                    }
                    break;
                case 5:
                    cacheSystem.displayCache();
                    break;
                case 6:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
