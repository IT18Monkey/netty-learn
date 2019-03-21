package com.github.it18monkey.arithmetic;

import java.util.Arrays;

public class QuickSort {
    public void sort(int arr[], int low, int high) {
        int l = low;
        int h = high;
        int povit = arr[low];

        while (l < h) {
            while (l < h && arr[h] >= povit) {
                h--;
            }
            if (l < h) {
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                l++;
            }

            while (l < h && arr[l] <= povit) {
                l++;
            }
            if (l < h) {
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                h--;
            }
        }
        System.out.println(Arrays.toString(arr));
        System.out.print("l=" + (l + 1) + "h=" + (h + 1) + "povit=" + povit + "\n");
        if (l - 1 > low) sort(arr, low, l - 1);
        if (h + 1 < high) sort(arr, h + 1, high);
    }


    public void sort2(int arr[], int low, int high) {
        int l = low;
        int h = high;
        int med = arr[low];

        while (l < h) {
            while (l < h) {
                if (arr[h] < med) {
                    int temp = arr[h];
                    arr[h] =arr[l];
                    arr[l] = temp;
                    l++;
                    break;
                } else {
                    h--;
                }
            }
            while (l < h) {
                if (arr[l] > med) {
                    int temp = arr[l];
                    arr[l] =arr[h];
                    arr[h] = temp;
                    h--;
                    break;
                } else {
                    l++;
                }
            }
        }
        System.out.println(Arrays.toString(arr));
        System.out.print("l=" + (l + 1) + "h=" + (h + 1) + "med=" + med + "\n");
        if (l - 1 > low) {
            sort2(arr, low, l - 1);
        }
        if (h + 1 < high) {
            sort2(arr, h + 1, high);
        }
    }

    public static void main(String[] args) {
        int[] arr = {3, 1, 4, 1, 2, 31, 4, 5, 6, 7, 13};
        new QuickSort().sort2(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
    }

}
