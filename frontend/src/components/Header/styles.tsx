import styled from 'styled-components';
import logo from '../../assets/logo - transparent.svg?react';

export const Container = styled.div`
  display: flex;
  height: 10%;
  background-color: #FF7A00;
  align-items: center;
  justify-content: space-between;
  padding: 0 1.5vw;
  color: #FFFFFF;

  @media (max-width: 768px) {
    padding: 0 3%;
    height: 11%;
  }
`;

export const LogoContainer = styled.a`
  display: flex;
  align-items: center;
  gap: 1vw;
  font-size: 1rem;
  font-weight: 600;

  &:hover,
  &:active {
    cursor: pointer;
    opacity: 0.8;
  }
`;

export const Logo = styled(logo)`
  width: auto;
  height: 3vh;
`;

export const NavLinksContainer = styled.div`
  display: flex;
  gap: 1vw;
`;

export const Link = styled.a`
  font-size: 1rem;
  font-weight: 600;

  &:hover,
  &:active {
    cursor: pointer;
    opacity: 0.8;
  }
`;
