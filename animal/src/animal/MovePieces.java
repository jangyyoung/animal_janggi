package animal;


public class MovePieces {
	   public boolean CheckMalMove(int[][] board, int i, int[] preposition, int[] nowposition)
	   {
		 //�����̷��� ���� �� �� ���
		   if (i % 10 == 1)
	      { if (King_move(preposition, nowposition)) // ���� ���� �ִ� ��ġ�� ���� ��ġ�� ���ؼ� King_move �Լ�?�� �����Ų��
	      	{ if(preposition[0] <5 && preposition[1] <4 )
	      		return true;
	       	} else
		    	  return false;
	      }
	     
		 //�����̷��� ���� ���� ���
		  else if (i % 10 == 2 || i % 10 == 3 ) 
		  {
	         if (sang_move(preposition, nowposition))
	        	 if(preposition[0] <5 && preposition[1] <4 )
	        		 return true;
	      	 else
		    	  return false;
		  }  
		  
		 //�����̷��� ���� ���� ���� ���
		  else if (i == 4 || i == 5) {
			  if ( R_ja_move(preposition, nowposition))
		        	 if(preposition[0] <5 && preposition[1] <4 )
		        		 return true;
		      	 else
			    	  return false;
			  }  
		 //�����̷��� ���� ��� ���� ���
		  else if (i == 14 || i == 15) {
			  if ( G_ja_move(preposition, nowposition))
		        return true;
		      	 else
			    	  return false;
			  }  
			 
		 //�����̷��� ���� ���� ���
	       else if (i % 10 == 6 || i % 10 == 7){
	    	   {
	  	         if (jang_move(preposition, nowposition))
	  	        		 return true;
	  	      	 else
	  		    	  return false;
	  		  }  
	    	   
	     //�����̷��� ���� ���� ���� ���
	      } else if (i == 8 || i == 9) {
			  if ( R_who_move(preposition, nowposition))
		        		 return true;
		      	 else
			    	  return false;
			  }  
		 //�����̷��� ���� ��� ���� ���
	      else if (i == 18 || i == 19) {
			  if ( G_who_move(preposition, nowposition))
		        	 if(preposition[0] <5 && preposition[1] <4 )
		        		 return true;
		      	 else
			    	  return false;
			  }  
		return false;  
	      }

	   
	   
	   
	//�ѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤѤ�
	
	   /*
		 
	   �� [i][j]���� [i][j+1], [i][j-1], [i+1][j], [i-1][j], [i][j-1], [i][j+1], [i+1][j+1], [i-1][j-1]�� �̵�����

	   �� [i][j]���� [i][j+1]�� �̵�����  ��/�� �ٸ�

	   �� [i][j]���� [i+1][j+1], [i+1][j-1], [i-1][j+1], [i-1][j-1]�� �̵�����

	   �� [i][j]���� [i+1][j] ,[i-1][j] , [i][j-1], [i][j+1]�� �̵�����

	   �� [i][j]���� [i-1][j-1], [i-1][j],[i-1][j+1],  [i][j+1], [i][j-1], [i+1][j]�� �̵����� ��/�� �ٸ�

	   	*/
	   
		//�� �̵����� ���� 
	   boolean King_move(int[] preposition, int[] nowposition) {
		   if(preposition[0] == nowposition[0]) //
		   {
			   if(preposition[1]+1 == nowposition[1])
				   return true;
			   else if(preposition[1]-1 == nowposition[1])
				   return true;
			   else
				   return false;
		   }
		   else if(preposition[0] + 1 == nowposition[0])	
		   {
			   if(preposition[1]+1 == nowposition[1])	
				   return true;
			   else if(preposition[1]-1 == nowposition[1])
				   return true;
			   else if(preposition[1] == nowposition[1])
				   return true;
			   else
				   return false;
		   }
		   else if(preposition[0] - 1 == nowposition[0])
		   {
			   if(preposition[1]+1 == nowposition[1])
				   return true;
			   else if(preposition[1]-1 == nowposition[1])
				   return true;
			   else if(preposition[1] == nowposition[1])
				   return true;
			   else
				   return false;
		   }
		   else
			   return false;
	   }
	   
		//�� �̵����� ����
	   boolean sang_move(int[] preposition, int[] nowposition) {
		   if(preposition[0] + 1 == nowposition[0]) 
		   {
			   if(preposition[1]+1 == nowposition[1])
				   return true;
			   else if(preposition[1]-1 == nowposition[1])
				   return true;
			   else
				   return false;
		   }
		   
		   else if(preposition[0] - 1 == nowposition[0])
		   {
			   if(preposition[1]+1 == nowposition[1])
				   return true;
			   else if(preposition[1]-1 == nowposition[1])
				   return true;
			   else
				   return false;
		   }
		   else
			   return false;
	   }

	   //���� �� �̵����ɹ���
	   boolean R_ja_move(int[] preposition, int[] nowposition) {
		   if(preposition[1] == nowposition[1]) //x��ǥ�� ����
		   {
			   if(preposition[0]+1 == nowposition[0])
				   return true;
			   else
				   return false;
		   }
		   else
			   return false;
	   }
		   
	   //��� �� �̵� ���� ����
	   boolean G_ja_move(int[] preposition, int[] nowposition) {
		   if(preposition[1] == nowposition[1]) //x��ǥ�� ����
		   {
			   if(preposition[0]-1 == nowposition[0])
				   return true;
			   else
					return false;
		   }
		   else
			   return false;

	   }
	   
	   //�� �̵����ɹ���
	   boolean jang_move(int[] preposition, int[] nowposition)
	   {
		   if(preposition[0] == nowposition[0]) //x��ǥ�� ����
		   {
			   if(preposition[1]+1 == nowposition[1])
				   return true;
			   if(preposition[1]-1 == nowposition[1])
				   return true;
			   else
				   return false;
		   }
		   else if(preposition[1] == nowposition[1]) //x��ǥ�� ����
		   {
			   if(preposition[0]+1 == nowposition[0])
				   return true;
			   if(preposition[0]-1 == nowposition[0])
				   return true;
			   else
				   return false;
		   }
		   else
			   return false;
	   }
	   
	   //���� �� �̵� ���� ����
	   boolean R_who_move(int[] preposition, int[] nowposition)
	   {
		   if(preposition[0]-1 == nowposition[0])
		   {
			   if(preposition[1]+1 == nowposition[1])
				   return true;
			   else if(preposition[1]-1 == nowposition[1])
				   return true;
			   else if(preposition[1] == nowposition[1])
				   return true; 
			   else
				   return false;
		   }
		   else if(preposition[0] == nowposition[0]) 
		   {
			   if(preposition[1]+1 == nowposition[1])
				   return true;
			   if(preposition[1]-1 == nowposition[1])
				   return true;
			   else
				   return false;
		   }
		   else if(preposition[0]+1 == nowposition[0])
		   {
			   if(preposition[1] == nowposition[1])
				   return true;
			   else
				   return false;
		   }
		return false;
	   }
	   
	   //�ʷϻ� �� �̵����� ����
	   public boolean G_who_move(int[] preposition, int[] nowposition)
	   {
		   if(preposition[0]+1 == nowposition[0])
		   {
			   if(preposition[1]-1 == nowposition[1])
				   return true;
			   else if(preposition[1]+1 == nowposition[1])
				   return true;
			   else if(preposition[1] == nowposition[1])
				   return true; 
			   else
				   return false;
		   }
		   else if(preposition[0] == nowposition[0])
		   {
			   if(preposition[1]-1 == nowposition[1])
				   return true;
			   if(preposition[1]+1 == nowposition[1])
				   return true;
			   else
				   return false;
		   }
		   else if(preposition[0]-1 == nowposition[0])
		   {
			   if(preposition[1] == nowposition[1])
				   return true;
			   else
				   return false;
		   }
		return false;

		
	   }
}
	      
	
