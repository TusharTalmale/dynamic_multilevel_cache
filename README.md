
## Dynamic Multilevel Caching System

### Overview
This project implements a **Dynamic Multilevel Caching System** that manages data across multiple cache levels with support for different eviction policies such as **Least Recently Used (LRU)** and **Least Frequently Used (LFU)**. The system efficiently stores and retrieves data while minimizing cache misses and ensuring data consistency.

#### Key Features:
- **Multi-level Cache Support:** Supports multiple cache levels (e.g., L1, L2, ..., Ln).
- **Eviction Policies:** Each cache level supports either LRU or LFU eviction policies.
- **Data Retrieval:** Retrieves data from higher priority caches first. If not found, it checks lower levels and promotes the data to higher levels.
- **Simultaneous Data Insertion:** Data is inserted into the highest-priority cache (L1) and also into other levels as needed.
- **Dynamic Management:** Cache levels can be added or removed at runtime.
- **In-memory Solution:** All data is stored and handled in-memory without external dependencies.
  
### How to Run

#### Prerequisites:
- **Java JDK** installed on your machine (version 8 or higher).

#### Instructions:
- Clone the repository:
   ```bash
   git clone https://github.com/TusharTalmale/dynamic_multilevel_cache/tree/main/src
- Or just copy the file Main.java and paste it in your src folder

### Example Output :-
![image](https://github.com/user-attachments/assets/a63a9552-13fb-4bbe-a726-f1be87f5b6ac)
![Screenshot 2024-09-09 223338](https://github.com/user-attachments/assets/7ce49273-e3fd-4a81-abf4-1e9b4527ba26)
![Screenshot 2024-09-09 223352](https://github.com/user-attachments/assets/379d6be0-c2f8-4f4b-b6a4-5416ed714d65)
![Screenshot 2024-09-09 223404](https://github.com/user-attachments/assets/0cc33105-762f-46c0-b52b-6082a0208757)

![Screenshot 2024-09-09 223418](https://github.com/user-attachments/assets/3ec5997f-2f9e-4959-98a1-c0ba8dd22421)

![Screenshot 2024-09-09 223434](https://github.com/user-attachments/assets/7bc40472-247e-4edd-9366-e86404091598)
![Screenshot 2024-09-09 223445](https://github.com/user-attachments/assets/05a68bd2-8d06-493f-99e6-48766e56bc73)
![Screenshot 2024-09-09 223456](https://github.com/user-attachments/assets/d7105b57-7819-476e-89e1-843565014f97)
![Screenshot 2024-09-09 223510](https://github.com/user-attachments/assets/af00dd9f-5a1c-4587-8e3a-cdc2beef3f5e)
