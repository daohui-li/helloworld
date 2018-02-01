package data.sort;

public class MergeSort {
    public static void sort(int[] data) {
        if (data==null) {
            throw new RuntimeException("null input");
        }
        sort(data, 0, data.length);
    }

    private static void sort(int[] data, int min, int max) {
        if (min + 1 >= max) {
            return;
        }

        int mid = (min+max)/2;
        sort(data, min, mid);
        sort(data, mid, max);

        merge(data, min, mid, max);
    }

    private static void merge(int[] data, int min, int mid, int max) {
        int[] tmp = new int[max-min];
        int left = min;
        int right = mid;
        for (int i = 0; i<tmp.length; i++) {
            if (left>=mid) { // end of left array
                tmp[i] = data[right++];
            } else if (right>=max) { // end of right array
                tmp[i] = data[left++];
            } else {
                if (data[right]<data[left]) {
                    tmp[i] = data[right++];
                } else {
                    tmp[i] = data[left++];
                }
            }
        }

        for (int i=0; i<tmp.length; i++) {
            data[min+i] = tmp[i];
        }
    }
}
