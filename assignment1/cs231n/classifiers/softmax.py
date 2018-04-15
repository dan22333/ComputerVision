import numpy as np
from random import shuffle
from past.builtins import xrange

def softmax_loss_naive(W, X, y, reg):
  """
  Softmax loss function, naive implementation (with loops)

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
  # Initialize the loss and gradient to zero.
  loss = 0.0
  dW = np.zeros_like(W)

  #############################################################################
  # TODO: Compute the softmax loss and its gradient using explicit loops.     #
  # Store the loss in loss and the gradient in dW. If you are not careful     #
  # here, it is easy to run into numeric instability. Don't forget the        #
  # regularization!                                                           #
  #############################################################################
  num_classes = W.shape[1]
  num_train = X.shape[0]

  for i in range(num_train):

      #compute weights dot product
      weight_product = X[i].dot(W)
      # shift of values to prevent overflow to infinity
      weight_product -= np.max(weight_product)
       # compute the exponents
      exp_product = np.exp(weight_product)
      #computer probability
      numerator = exp_product[y[i]]
      denomenator = np.sum(exp_product)
      #add loss
      loss += - np.log(numerator / denomenator)

      # grad
      # for correct class
      for j in range(num_classes):
          # pass correct class gradient
          if j == y[i]:
             # for correct classes the derivative is -X[i](1-p(j))
             dW[:, y[i]] += (-1) *(1-(numerator/denomenator))* X[i]
          else:
            # for incorrect classes the derivative is p(j)X[i]
            dW[:, j] += (exp_product[j]/denomenator) * X[i]
  #divide by number of traning examples
  dW /= num_train
  #add regrulization derivative
  dW += 2 * reg * W
  #divide by number of traning examples
  loss /= num_train
  #add loss from regulization
  loss += reg * np.sum(W * W)
  #############################################################################
  #                          END OF YOUR CODE                                 #
  #############################################################################

  return loss, dW


def softmax_loss_vectorized(W, X, y, reg):
  """
  Softmax loss function, vectorized version.

  Inputs and outputs are the same as softmax_loss_naive.
  """
  # Initialize the loss and gradient to zero.
  loss = 0.0
  dW = np.zeros_like(W)

  #############################################################################
  # TODO: Compute the softmax loss and its gradient using no explicit loops.  #
  # Store the loss in loss and the gradient in dW. If you are not careful     #
  # here, it is easy to run into numeric instability. Don't forget the        #
  # regularization!                                                           #
  #############################################################################
  num_classes = W.shape[1]
  num_train = X.shape[0]
  #compute products
  weight_product = np.dot(X,W)
  #compute exp part of fractions
  exp_product = np.apply_along_axis(lambda x: np.exp(x - np.max(x)), 1,weight_product)
  #trun into proabilities
  probabilites= np.apply_along_axis(lambda x: x / np.sum(x), 1,exp_product)
  #compute loss
  loss = np.sum(-np.log(probabilites[np.arange(num_train), y]))
  
  indices = np.zeros_like(probabilites)
  #mark locations of correct index
  indices[np.arange(num_train), y] = 1
  #derivate is x*p(j) for incorrrect labels and x(p(j)-1) for correct labels
  prob_for_gradient = probabilites - indices
  dW = np.dot(X.T,probabilites - indices)

  #divide by number of traning examples
  dW /= num_train
  #add regrulization derivative
  dW += 2 * reg * W
  #divide by number of traning examples
  loss /= num_train
  #add loss from regulization
  loss += reg * np.sum(W * W)
  #############################################################################
  #                          END OF YOUR CODE                                 #
  #############################################################################

  return loss, dW

