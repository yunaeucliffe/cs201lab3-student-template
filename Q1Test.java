public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     }

     // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3) {
          String[] traversals = { traversal1, traversal2, traversal3 };
          int[][] nums = new int[3][];

          // Convert the strings into integer arrays
          for (int i = 0; i < 3; i++) {
               String[] parts = traversals[i].split("-");
               nums[i] = new int[parts.length];

               for (int j = 0; j < parts.length; j++) {
                    nums[i][j] = Integer.parseInt(parts[j]);
               }
          }

          // All traversals must have the same number of nodes
          int n = nums[0].length;
          if (nums[1].length != n || nums[2].length != n) {
               return "Invalid traversals";
          }

          // Identify inorder (must be sorted for a BST)
          int inorderIndex = -1;

          for (int i = 0; i < 3; i++) {
               boolean sorted = true;

               for (int j = 1; j < n; j++) {
                    if (nums[i][j] <= nums[i][j - 1]) {
                         sorted = false;
                         break;
                    }
               }

               if (sorted) {
                    inorderIndex = i;
                    break;
               }
          }

          if (inorderIndex == -1) {
               return "Invalid traversals";
          }

          // Find the remaining two traversals
          int a = (inorderIndex + 1) % 3;
          int b = (inorderIndex + 2) % 3;

          int preorderIndex = -1;
          int postorderIndex = -1;

          // Try a as preorder and b as postorder
          if (check(nums[inorderIndex], nums[a], nums[b], 0, n - 1, 0, n - 1, 0, n - 1)) {
               preorderIndex = a;
               postorderIndex = b;
          }
          // Try b as preorder and a as postorder
          else if (check(nums[inorderIndex], nums[b], nums[a], 0, n - 1, 0, n - 1, 0, n - 1)) {
               preorderIndex = b;
               postorderIndex = a;
          } else {
               return "Invalid traversals";
          }

          String[] types = new String[3];
          types[inorderIndex] = "Inorder";
          types[preorderIndex] = "Preorder";
          types[postorderIndex] = "Postorder";

          return "Traversal 1 - " + types[0]
                    + ", Traversal 2 - " + types[1]
                    + ", Traversal 3 - " + types[2];
     }

     public static boolean check(int[] in, int[] pre, int[] post,
               int inStart, int inEnd,
               int preStart, int preEnd,
               int postStart, int postEnd) {

          if (inStart > inEnd) {
               return true;
          }

          // Preorder starts with root, postorder ends with root
          int root = pre[preStart];

          if (root != post[postEnd]) {
               return false;
          }

          // Find root in inorder
          int rootIndex = -1;

          for (int i = inStart; i <= inEnd; i++) {
               if (in[i] == root) {
                    rootIndex = i;
                    break;
               }
          }

          if (rootIndex == -1) {
               return false;
          }

          int leftSize = rootIndex - inStart;

          // Check left and right subtrees
          return check(in, pre, post,
                    inStart, rootIndex - 1,
                    preStart + 1, preStart + leftSize,
                    postStart, postStart + leftSize - 1)
                    && check(in, pre, post,
                              rootIndex + 1, inEnd,
                              preStart + leftSize + 1, preEnd,
                              postStart + leftSize, postEnd - 1);
     }
}