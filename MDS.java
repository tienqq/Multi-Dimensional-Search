// Tien Quang (txq170130)

package rbk;
import java.util.*;

// If you want to create additional classes, place them in this file as subclasses of MDS

public class MDS {

    // Add fields of MDS here
	//hashmap that will use ID as the key and price as a value
	public HashMap<Integer, Integer> priceHm ;
	//hashmap that will use ID as key and put set of ints of the description into a treeset
	public HashMap<Integer, TreeSet<Integer>> desHm;
	
	
    // Constructors
    public MDS() {
    	priceHm = new HashMap<Integer, Integer>();
    	desHm = new HashMap<Integer, TreeSet<Integer>>();
    }

    
    /* Public methods of MDS. Do not change their signatures.
       __________________________________________________________________
       a. Insert(id,price,list): insert a new item whose description is given
       in the list.  If an entry with the same id already exists, then its
       description and price are replaced by the new values, unless list
       is null or empty, in which case, just the price is updated. 
       Returns 1 if the item is new, and 0 otherwise.
    */
    public int insert(int id, int price, java.util.List<Integer> list) {
    		//copy list over to a temp treeset
		TreeSet<Integer> temp = new TreeSet<Integer>();
		Iterator <Integer> it = list.iterator();
		while(it.hasNext()) {
			temp.add(it.next());
		}
			
    		//new ID-> checks if id exists in each hashmaps and if the list is empty or null -> add price and list to their respective hashmap
    		if(!priceHm.containsKey(id) && !desHm.containsKey(id) && (!list.isEmpty() || !(list == null))) {
    			priceHm.put(id, price);
    			desHm.put(id, temp);
    			return 1;	
        		}
    	
    		//Hashmaps contains ids and the list is neither empty or null
    		else if (priceHm.containsKey(id) && desHm.containsKey(id) && (!list.isEmpty() || !(list == null))){
    			//replacing existing id's with new values
    			priceHm.replace(id, price);
    			desHm.replace(id, temp);
    			return 0;
    			}
    		//id exists but the list is empty or null
    		else {
    			//update the price
    			priceHm.replace(id, price);
    			return 0;
    		}
    	}

    // b. Find(id): return price of item with given id (or 0, if not found).
    public int find(int id) {
    		//ID does not exist
    		if(!priceHm.containsKey(id)) {
    			return 0;
    		}
    		//ID exist -> return price
    		else {
    			return priceHm.get(id);
    		}
    }

    /* 
       c. Delete(id): delete item from storage.  Returns the sum of the
       ints that are in the description of the item deleted,
       or 0, if such an id did not exist.
    */
    public int delete(int id) {
    		//ID doesn't exist
    		if(!priceHm.containsKey(id) && !desHm.containsKey(id)) {
    			return 0;
    		}
    		//ID exist
    		else {
    			//getting the sum of the description
    			int sum = 0;
    			TreeSet<Integer> list = desHm.get(id);
    			Iterator<Integer> it = list.iterator();
    			while(it.hasNext()) {
    				sum += it.next();
    			}
    			//delete id from hashmaps
    			priceHm.remove(id);
    			desHm.remove(id);
    			return sum;
    		}
    }
    /* 
       d. FindMinPrice(n): given an integer, find items whose description
       contains that number (exact match with one of the ints in the
       item's description), and return lowest price of those items.
       Return 0 if there is no such item.
    */
    public int findMinPrice(int n) {
    		int low;
    		//idlist will contain the ids that has n in it's description
    		LinkedList<Integer> idlist = new LinkedList<Integer>();
    		TreeSet<Integer> des = new TreeSet<Integer>();
    		//iterate through the description hashmap to find if n is in it
    		for (Map.Entry<Integer, TreeSet<Integer>> entry: desHm.entrySet()) {
    			des = entry.getValue();
    			if(des.contains(n)) {
    				//add key value to idlist
    				idlist.add(entry.getKey());
    				}
    			}
    		//if idlist is empty then that means there is no id that contains n
    		if(idlist.isEmpty()) {
    			return 0;
    		}
 		//else, get the price from the list of ids that contains n and compare to get the lowest
    		else {
    			Iterator<Integer> it = idlist.iterator();
    			//initial price is the first id's price in the list
    			low = priceHm.get(it.next());
    				while(it.hasNext()) {
    			
    				//if the next ids price is lower then it becomes the new low
    				int temp = priceHm.get(it.next());
    				if(low > temp) {
    					low = temp;	
    				}
    			}
    			
    		return low;
    		}
    }

    /* 
       e. FindMaxPrice(n): given an integer, find items whose description
       contains that number, and return highest price of those items.
       Return 0 if there is no such item.
    */
    public int findMaxPrice(int n) {
    		int high;
		//idlist will contain the ids that has n in it's description
		LinkedList<Integer> idlist = new LinkedList<Integer>();
		TreeSet<Integer> des = new TreeSet<Integer>();
		//iterate through the description hashmap to find if n is in it
		for (Map.Entry<Integer, TreeSet<Integer>> entry: desHm.entrySet()) {
			des = entry.getValue();
			if(des.contains(n)) {
				//add key value to idlist
				idlist.add(entry.getKey());
				}
			}
		//if idlist is empty then that means there is no id that contains n
		if(idlist.isEmpty()) {
			return 0;
		}
		//else, get the price from the list of ids that contains n and compare to get the lowest
		else {
			Iterator<Integer> it = idlist.iterator();
			//initial price is the first id's price in the list
			high = priceHm.get(it.next());
			while(it.hasNext()) {
				//if the next ids price is higher than current high, it becomes the new high
				int temp = priceHm.get(it.next());
				if(high < temp) {
					high = temp;	
				}
			}
		return high;
		}
    }

    /* 
       f. FindPriceRange(n,low,high): given int n, find the number
       of items whose description contains n, and in addition,
       their prices fall within the given range, [low, high].
    */
    public int findPriceRange(int n, int low, int high) {
    		//number that will be compared to low and high
    		int num;
    		int range = 0;
    		//list of ids whose price value are between low and high
    		LinkedList<Integer>idlist = new LinkedList<Integer>();
    		TreeSet<Integer> des = new TreeSet<Integer>();
    		//iterate through deshashmap to get ids that contain n
    		for (Map.Entry<Integer, TreeSet<Integer>> entry: desHm.entrySet()) {
    			des = entry.getValue();
    			if(des.contains(n)) {
    				//add key value to idlist
    				idlist.add(entry.getKey());
    				}
    			}
    		//no ids contains n
    		if(idlist.isEmpty()) {
    			return 0;
    		}
    		else {
    			Iterator<Integer> it = idlist.iterator();
    			while(it.hasNext()) {
    				//price of current id
    				num = priceHm.get(it.next());
    				//if price >= low and price =< high then it is within the price range and gets sent to idlist
    				if(num >= low && num <= high) {
    					range++;
    				}
    			}
    			return range;
    		}
    }

    /*
      g. RemoveNames(id, list): Remove elements of list from the description of id.
      It is possible that some of the items in the list are not in the
      id's description.  Return the sum of the numbers that are actually
      deleted from the description of id.  Return 0 if there is no such id.
    */
    public int removeNames(int id, java.util.List<Integer> list) {
    		//no such id
    		if(!priceHm.containsKey(id) && !desHm.containsKey(id)) {
    			return 0;
    		}
    		//id exists, get sum of deleted description numbers
    		else {
    			int sum = 0;
    			TreeSet<Integer> des = desHm.get(id);
    			Iterator<Integer> it = list.iterator();
    			//if the treeset of des contains a number from the list then add it to sum then delete it
    			while(it.hasNext()) {
    				if(des.contains(it.next())) {
    				sum += it.next();
    				des.remove(it.next());
    				}
    			}
    			//replace description
    			desHm.replace(id, des);
    			return sum;
    		}
    }
}
