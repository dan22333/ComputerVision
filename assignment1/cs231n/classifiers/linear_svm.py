import numpy as np
from random import shuffle
from past.builtins import xrange

def svm_loss_naive(W, X, y, reg):
  """
  Structured SVM loss function, naive implementation (with loops).

  Inputs have dimension D, there are C classes, and we operate on minibatches
  of N examples.

  Inputs:
  - W: A numpy array of shape (D, C) containing weights.
  - X: A numpy array of shape (N, D) containing a minibatch of data.
  - y: A numpy array of shape (N,) containing training labels; y[i] = c means
    that X[i] has label c, where 0 <= c < C.
  - reg: (float) regularization strength

  Returns a tuple of:
  - loss as single float
  - gradient with respect to weights W; an array of same shape as W
  """
  dW = np.zeros(W.shape) # initialize the gradient as zero

  # compute the loss and the gradient
  num_classes = W.shape[1]
  num_train = X.shape[0]
  loss = 0.0
  for i in xrange(num_train):
    scores = X[i].dot(W)
    correct_class_score = scores[y[i]]
    for j in xrange(num_classes):
      if j == y[i]:
        continue
      margin = scores[j] - correct_class_score + 1 # note delta = 1
      if margin > 0:
        dW[:, j] += X[i]  #adding on each itteration the line that suits the training sample we are checking. We add those values to the colume on the gradient that isn't the correct class.
        dW[:, y[i]] += -1* X[i]
        loss += margin

  # Right now the loss is a sum over all training examples, but we want it
  # to be an average instead so we divide by num_train.
  loss /= num_train

  # Add regularization to the loss.
  loss += reg * np.sum(W * W)

  #############################################################################
  # TODO:                                                                     #
  # Compute the gradient of the loss function and store it dW.                #
  # Rather that first computing the loss and then computing the derivative,   #
  # it may be simpler to compute the derivative at the same time that the     #
  # loss is being computed. As a result you may need to modify some of the    #
  # code above to compute the gradient.                                       #
  #############################################################################
  #pick the average here as well
  dW /= num_train
  dW += 2*reg*W # regularize the weights

  return loss, dW


def svm_loss_vectorized(W, X, y, reg):
  """
  Structured SVM loss function, vectorized implementation.

  Inputs and outputs are the same as svm_loss_naive.
  """
  loss = 0.0
  W_new = W.T #transpose the DATA for easier calculation
  X_new = X.T
  dW = np.zeros(W.shape) # initialize the gradient as zero

  #############################################################################
  # TODO:                                                                     #
  # Implement a vectorized version of the structured SVM loss, storing the    #
  # result in loss.                                                           #
  #############################################################################
  
  vec_scores = np.dot(W_new,X_new) # the result is shape of (49000,10)
  correct_vec_scores = np.ones(vec_scores.shape) * vec_scores[y, np.arange(0,vec_scores.shape[1])] 
  vec_deltas = np.ones(vec_scores.shape) # note delta =1 (like on the naive version)
  L = vec_scores - correct_vec_scores + vec_deltas # similar to the naive version, where we got the margin on each eateration and then checked whethere it's greater then zero.
  
  L[L < 0] = 0 #checking if the margin is greater then 0.
  L[y, np.arange(0,vec_scores.shape[1])] = 0 # like on the naive way when we made dW[:, y[i]] += -1* X[i]. we don't want to take y[i] in to account
  loss = np.sum(L)
  
  num_train = X_new.shape[1]
  loss /= num_train
  
  loss +=  reg * np.sum(W * W)
  
  
  
  #############################################################################
  #                             END OF YOUR CODE                              #
  #############################################################################


  #############################################################################
  # TODO:                                                                     #
  # Implement a vectorized version of the gradient for the structured SVM     #
  # loss, storing the result in dW.                                           #
  #                                                                           #
  # Hint: Instead of computing the gradient from scratch, it may be easier    #
  # to reuse some of the intermediate values that you used to compute the     #
  # loss.                                                                     #
  #############################################################################
  grad = np.zeros(vec_scores.shape)
  
  L = vec_scores - correct_vec_scores + vec_deltas #the same as on vectorized loss
  L[L < 0] = 0
  L[L > 0] = 1
  L[y, np.arange(0, vec_scores.shape[1])] = 0
  L[y, np.arange(0, vec_scores.shape[1])] = -1 * np.sum(L, axis=0)
  dW = np.dot(L, X_new.T)
  
  num_train = X_new.shape[1] 
  dW /= num_train # take average
  dW=dW.T # transpose the gradient to match the original SVM code
  dW += 2*reg*W
  
  #############################################################################
  #                             END OF YOUR CODE                              #
  #############################################################################

  return loss, dW

