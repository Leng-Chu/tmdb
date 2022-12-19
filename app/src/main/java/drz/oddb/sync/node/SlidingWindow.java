package drz.oddb.sync.node;

import java.util.ArrayList;


/*
* 1. 队列非空时才能移除元素
* 2. 队列非满时才能添加元素
* */
public class SlidingWindow {

    private final int maxNum;//数组最大长度

    private int nextIndex = 0;//数组中下一个待填入元素的索引值，即队尾指针

    private QueueEntry[] syncQueue;

    private int left = 0;//窗口左端索引

    private int windowSize;//窗口大小

    private int right;//窗口右端索引



    public SlidingWindow(int maxNum, int windowSize) {
        this.maxNum = maxNum;
        this.windowSize = windowSize;
        right = left + windowSize - 1;
        syncQueue = new QueueEntry[maxNum];
    }

    public QueueEntry[] getSyncQueue() {
        return syncQueue;
    }

    public int getLeft() {
        return left;
    }

    public int getWindowSize() {
        return windowSize;
    }

    public void setWindowSize(int windowSize) {
        this.windowSize = windowSize;
    }

    public int getRight() {
        return right;
    }



    public boolean isEmpty(){
        return (nextIndex == left);
    }

    //牺牲一个元素空间用于判断整个数据区是否已满
    public boolean isFull(){
        return (((nextIndex + 1) % maxNum) == left);
    }


    public void putRequest(RequestType requestType, Long key){


        QueueEntry entry = new QueueEntry(requestType,key);
        syncQueue[nextIndex] = entry;

        nextIndex = (nextIndex + 1) % maxNum;


    }

    //移除窗口首部元素
    public void removeRequest(){
        left = (left + 1) % maxNum;
        right = (right + 1) % maxNum;
    }

    //获取窗口首部的元素
    public QueueEntry getHeadEntry(){
        return syncQueue[left];
    }

    //获取滑动窗口内的所有元素
    public ArrayList<QueueEntry> getAllEntries(){
        ArrayList<QueueEntry> result = new ArrayList<>(windowSize);

        for(int i = left;i <= right; i++){
            result.add(syncQueue[i]);
        }

        return result;
    }



}