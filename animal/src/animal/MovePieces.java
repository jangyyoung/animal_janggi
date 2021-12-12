package animal;


public class MovePieces {
	   public boolean CheckMalMove(int[][] board, int i, int[] preposition, int[] nowposition)
	   {
		 //움직이려는 말이 왕 일 경우
		   if (i % 10 == 1)
	      { if (King_move(preposition, nowposition)) // 만약 전에 있던 위치와 현재 위치에 관해서 King_move 함수?를 적용시킨다
	      	{ if(preposition[0] <5 && preposition[1] <4 )
	      		return true;
	       	} else
		    	  return false;
	      }
	     
		 //움직이려는 말이 상일 경우
		  else if (i % 10 == 2 || i % 10 == 3 ) 
		  {
	         if (sang_move(preposition, nowposition))
	        	 if(preposition[0] <5 && preposition[1] <4 )
	        		 return true;
	      	 else
		    	  return false;
		  }  
		  
		 //움직이려는 말이 빨간 자일 경우
		  else if (i == 4 || i == 5) {
			  if ( R_ja_move(preposition, nowposition))
		        	 if(preposition[0] <5 && preposition[1] <4 )
		        		 return true;
		      	 else
			    	  return false;
			  }  
		 //움직이려는 말이 녹색 자일 경우
		  else if (i == 14 || i == 15) {
			  if ( G_ja_move(preposition, nowposition))
		        return true;
		      	 else
			    	  return false;
			  }  
			 
		 //움직이려는 말이 장일 경우
	       else if (i % 10 == 6 || i % 10 == 7){
	    	   {
	  	         if (jang_move(preposition, nowposition))
	  	        		 return true;
	  	      	 else
	  		    	  return false;
	  		  }  
	    	   
	     //움직이려는 말이 빨간 후일 경우
	      } else if (i == 8 || i == 9) {
			  if ( R_who_move(preposition, nowposition))
		        		 return true;
		      	 else
			    	  return false;
			  }  
		 //움직이려는 말이 녹색 후일 경우
	      else if (i == 18 || i == 19) {
			  if ( G_who_move(preposition, nowposition))
		        	 if(preposition[0] <5 && preposition[1] <4 )
		        		 return true;
		      	 else
			    	  return false;
			  }  
		return false;  
	      }

	   
	   
	   
	//ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
	
	   /*
		 
	   왕 [i][j]에서 [i][j+1], [i][j-1], [i+1][j], [i-1][j], [i][j-1], [i][j+1], [i+1][j+1], [i-1][j-1]로 이동가능

	   자 [i][j]에서 [i][j+1]로 이동가능  빨/녹 다름

	   상 [i][j]에서 [i+1][j+1], [i+1][j-1], [i-1][j+1], [i-1][j-1]로 이동가능

	   장 [i][j]에서 [i+1][j] ,[i-1][j] , [i][j-1], [i][j+1]로 이동가능

	   후 [i][j]에서 [i-1][j-1], [i-1][j],[i-1][j+1],  [i][j+1], [i][j-1], [i+1][j]로 이동가능 빨/녹 다름

	   	*/
	   
		//왕 이동가능 방향 
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
	   
		//상 이동가능 방향
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

	   //빨간 자 이동가능방향
	   boolean R_ja_move(int[] preposition, int[] nowposition) {
		   if(preposition[1] == nowposition[1]) //x좌표가 같음
		   {
			   if(preposition[0]+1 == nowposition[0])
				   return true;
			   else
				   return false;
		   }
		   else
			   return false;
	   }
		   
	   //녹색 자 이동 가능 방향
	   boolean G_ja_move(int[] preposition, int[] nowposition) {
		   if(preposition[1] == nowposition[1]) //x좌표가 같음
		   {
			   if(preposition[0]-1 == nowposition[0])
				   return true;
			   else
					return false;
		   }
		   else
			   return false;

	   }
	   
	   //장 이동가능방향
	   boolean jang_move(int[] preposition, int[] nowposition)
	   {
		   if(preposition[0] == nowposition[0]) //x좌표가 같음
		   {
			   if(preposition[1]+1 == nowposition[1])
				   return true;
			   if(preposition[1]-1 == nowposition[1])
				   return true;
			   else
				   return false;
		   }
		   else if(preposition[1] == nowposition[1]) //x좌표가 같음
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
	   
	   //빨간 후 이동 가능 방향
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
	   
	   //초록색 후 이동가능 방향
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
	      
	
